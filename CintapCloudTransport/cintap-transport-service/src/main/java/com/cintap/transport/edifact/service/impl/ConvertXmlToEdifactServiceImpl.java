package com.cintap.transport.edifact.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cintap.transport.aperak.outbound.StandardOutboundAperakService;
import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.PARSERTYPE;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.outbound.StandardOutboundShipNoticeEdifactService;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConvertXmlToEdifactServiceImpl{

	@Autowired
	TransactionLogRepository transactionLogRepository;
	
	@Autowired
	StandardOutboundAperakService standardOutboundAperakEdifactService;
	
	@Autowired
	StandardOutboundShipNoticeEdifactService standardOutboundShipNoticeEdifactService;
	
	@Autowired
	TransportCommonService transportCommonService;
	
	public String generateOutboundEdifact(Integer bpiLogId) throws Exception {
		log.info("ConvertXmlToEdifactServiceImpl | generateOutboundEdifact - Request - BpiLogId : {}",bpiLogId);
		String result=null;
		Optional<TransactionLog> optTransactionLog = transactionLogRepository.findByBpiLogId(bpiLogId);
		if(optTransactionLog.isPresent()) {
			TransactionLog transactionLog = optTransactionLog.get();
			List<BpiLogDetail> lstLogDetails = new ArrayList<BpiLogDetail>();
			lstLogDetails.add(BpiLogDetail.builder().logType("INFO").logDetails("OUTBOUND  XML generation for "+transactionLog.getStpSourceId()).build());
			FileUploadParams fileUploadParams = FileUploadParams.builder().fileName("fileName").partnerId(transactionLog.getStpId())
					.senderPartnerId(transactionLog.getStpId()).ediType("EDIFACT").ediVersion("97A")
					.receiverPartnerId(transactionLog.getRtpId()).receiverIsaId("zz").senderIsaId("zz").fileType("EDIFACT")
					.actualFileType("EDIFACT").parseType(PARSERTYPE.STANDARD.getValue())
					.source(TRANSACTIONSOURCE.IMPORT.getValue()).fileCount(1).ediStandard("EDIFACT").trnType(transactionLog.getTransactionType())
					.direction(DIRECTION.OUTBOUND.getDirection()).lstLogDetails(lstLogDetails).build();
			
			fileUploadParams = transportCommonService.bpiLogging(fileUploadParams);
			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					fileUploadParams.getFileType().toUpperCase()+" "+TRANSPORTLOG.PROCESS_OUTBOUND.getValue(),null);
			
//			result = standardOutboundAperakEdifactService.processOutboundRequest(bpiLogId, fileUploadParams, null);
			result = standardOutboundShipNoticeEdifactService.processOutboundRequest(bpiLogId, fileUploadParams, null);
			
//			switch (transactionLog.getStpSourceId()) {
//			case "DESADV":
//				result = standardOutboundDesAdvAsnXmlService.processOutboundRequest(bpiLogId, fileUploadParams, null);
//				break;
//			case "ORDERS":
//				result= standardOutboundOrdersXmlService.processOutboundRequest(bpiLogId, fileUploadParams, null);
//				break;
////			case "855":
////				result=getStandardOutboundOrdRespXmlService().outboundOrderRespXml(transactionLog);
////				break;
//			default:
//				break;
//			}
		}else {
			log.info(bpiLogId+" :: TRANSACTION DOESNOT FOUND");
		}
		log.info("ConvertXmlToEdifactServiceImpl |  generateOutboundEdifact - Response : {}",result);
		return result;
	}
}
