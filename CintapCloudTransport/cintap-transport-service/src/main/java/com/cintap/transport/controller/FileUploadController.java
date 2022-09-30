package com.cintap.transport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cintap.transport.aperak.inbound.StandardInboundAperakXmlService;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.PARSERTYPE;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.desadv.shipnotice.xml.inbound.StandardInboundShipNoticeXmlService;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.edifact.service.DespatchAdviceService;
import com.cintap.transport.edifact.service.OrdersService;
import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.iftman.inbound.StandardInboundReceivingAdviceXmlService;
import com.cintap.transport.model.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class FileUploadController {

	@Autowired
	DespatchAdviceService desadvService;

	@Autowired
	OrdersService ordersService;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	StandardInboundAperakXmlService standardInboundAperakXmlService;
	
	@Autowired
	StandardInboundShipNoticeXmlService shipNoticeXmlService;
	
	@Autowired
	StandardInboundReceivingAdviceXmlService standardInboundReceivingAdviceXmlService;

	@PostMapping(value = "/fileUpload") // ,consumes = {"application/json; charset=UTF-8"}
	public ApiResponse transactionFileUpload(@RequestParam MultipartFile file, String receiverPartnerId,
			String senderPartnerId, String transactionType) throws IOException {

		Integer batchId = null;
		ApiResponse apiResponse = null;
		FileUploadParams fileUploadParams = null;
		log.info("EdiFileUploadController | transactionFileUpload :  Request : file - " + file + " partnerId - "
				+ senderPartnerId);
		try {
			String fileName = null;
			String data = null;
			// DespatchAdviceHeader dtoResponse =null;
			Integer bpiLogId = 0;
			if (file != null) {
				fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
				byte[] bytes = file.getBytes();
				data = new String(bytes);
				List<BpiLogDetail> lstLogDetails = new ArrayList<BpiLogDetail>();
				lstLogDetails.add(BpiLogDetail.builder().logType("INFO")
						.logDetails("INBOUND  XML generation for " + transactionType).build());
				fileUploadParams = FileUploadParams.builder().fileName(fileName).partnerId(senderPartnerId)
						.senderPartnerId(senderPartnerId).ediType("edifact").ediVersion("97A")
						.receiverPartnerId(receiverPartnerId).receiverIsaId("zz").senderIsaId("zz").fileType("XML")
						.actualFileType("edifact").parseType(PARSERTYPE.STANDARD.getValue())
						.source(TRANSACTIONSOURCE.IMPORT.getValue()).fileCount(1).ediStandard("EDIFACT")
						.trnType(transactionType).direction(DIRECTION.INBOUND.getDirection())
						.lstLogDetails(lstLogDetails).build();

				fileUploadParams = transportCommonService.bpiLogging(fileUploadParams);

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						fileUploadParams.getFileType().toUpperCase() + " "
								+ TRANSPORTLOG.IMPORT_FILE_REQUEST_RECEIVED.getValue(),
						null);

				EdifactReqParam edifactReqParam = EdifactReqParam.builder().ediFactData(data)
						.receiverPartnerId(receiverPartnerId).senderPartnerId(senderPartnerId).build();
				if (transactionType.equals("DESADV")) {
					fileUploadParams = desadvService.parseAsn(edifactReqParam, fileUploadParams);
//					bpiLogId = dtoResponse.getBpiLogId();
				} else if (transactionType.equals("ORDERS")) {
					fileUploadParams = ordersService.parseOrder(edifactReqParam, fileUploadParams);
//					bpiLogId = dtoResponse.getBpiLogId();
				} else if (transactionType.equals("SHIP_NOTICE")||transactionType.equals("IFTSTA")) {
					fileUploadParams = shipNoticeXmlService.processInboundXMLRequest(data, fileUploadParams);
//					bpiLogId = dtoResponse.getBpiLogId();
				} else if (transactionType.equals("IFTMAN")) {
					fileUploadParams = standardInboundReceivingAdviceXmlService.processInboundXMLRequest(data, fileUploadParams);
//					bpiLogId = dtoResponse.getBpiLogId();
				} else if ("APERAK".equals(transactionType)) {
					fileUploadParams = standardInboundAperakXmlService.processInboundXMLRequest(data, fileUploadParams);
				}
			
			apiResponse = ApiResponse.builder().statusCode("0000").statusMessage(fileUploadParams.getBpiLogId() + "")
					.build();
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.SUCCESS.getStatusValue());
			}
		} catch (

		Exception exception1) {
			log.info("EDI File UploadController :: Exception :: {}", exception1);
		}
		log.info("EdiFileUploadController | transactionFileUpload : Response - "
				+ TransportCommonUtility.convertObjectToJson(apiResponse));
		return apiResponse;
	}

}