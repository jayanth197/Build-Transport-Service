package com.cintap.transport.parsing.service;

import java.util.Optional;

import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.EDIFACTDOCUMENTTYPE;
import com.cintap.transport.common.enums.EDIFACTTRANSACTIONTYPE;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.CommonServiceImpl;
import com.cintap.transport.common.service.StandardInboundService;
import com.cintap.transport.common.util.EditUtility;
import com.cintap.transport.entity.common.ProcessExecutionLog;
import com.cintap.transport.entity.common.TransportServiceMapping;
import com.cintap.transport.inbound.StandardInboundDesAdvAsnEdiFactService;
import com.cintap.transport.message.model.FileProcessResponse;
import com.cintap.transport.orders.inbound.StandardInboundOrdersEdifactService;
import com.cintap.transport.repository.common.TransportServiceMappingRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StandardInboundFileProcessService {

	@Autowired
	StandardInboundDesAdvAsnEdiFactService standardInboundDesAdvAsnEdiFactService;

	@Autowired
	StandardInboundOrdersEdifactService standardInboundOrdersEdifactService;

	@Autowired
	CommonServiceImpl commonServiceImpl;

	@Autowired
	TransportServiceMappingRepository transportServiceMappingRepository;

	@Autowired
	ApplicationContext applicationContext;

	private static final String forwarded = "Status=\"Forwarded\"";
	private static final String received = "Status=\"Received\"";

	public FileProcessResponse processEdiFile(String ediData, FileUploadParams fileUploadParams,
			ProcessExecutionLog processExecutionLog) {
		log.info("StandardInboundFileProcessService | processEdiFile - Request : EdiData - " + ediData
				+ "FileUploadParams - " + fileUploadParams);
		Integer batchId = null;
		FileProcessResponse fileProcessResponse = new FileProcessResponse();

		log.info("StandardInboundFileProcessService | processEdiFile- Response : {}", batchId);
		return fileProcessResponse;
	}

	public FileUploadParams processEdifactFile(String edifactData, FileUploadParams fileUploadParams,
			ProcessExecutionLog processExecutionLog) throws Exception {
		log.info("StandardInboundFileProcessService | processEdifactFile - Request : EdifactData - " + edifactData
				+ "FileUploadParams - " + fileUploadParams);
		Integer batchId = null;
		FileProcessResponse fileProcessResponse = new FileProcessResponse();
		String interchanges = EditUtility.convertEditToJosn(edifactData);
		
		String documentType = getEDIFACTDocumentType(interchanges);

		fileUploadParams.setEdiType(documentType);
		fileUploadParams.setTrnType(documentType);

		Optional<TransportServiceMapping> optTransportServiceMapping = transportServiceMappingRepository
				.findBySenderPartnerIdAndReceiverPartnerIdAndDirectionAndTransactionType(
						Integer.parseInt(fileUploadParams.getSenderPartnerId()),
						Integer.parseInt(fileUploadParams.getReceiverPartnerId()), DIRECTION.INBOUND.getDirection(),
						documentType);

		if (optTransportServiceMapping.isPresent()) {

			TransportServiceMapping transportServiceMapping = optTransportServiceMapping.get();

			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_IDENTIFIED.getValue() + " : "
							+ transportServiceMapping.getServiceClassName(),
					null);

			log.info("StandardInboundFileProcessService : transportServiceMapping :: "
					+ transportServiceMapping.toString());

			StandardInboundService standardService = (StandardInboundService) applicationContext
					.getBean(transportServiceMapping.getServiceClassName());

			fileUploadParams = standardService.processInboundRequest(interchanges, fileUploadParams);

		} else {
			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue(), null);

			log.info("StandardInboundFileProcessService : transportServiceMapping :: "
					+ TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue());
		}

//		switch (fileUploadParams.getTrnType()) {
//			case "DESADV":
//				fileUploadParams =standardInboundDesAdvAsnEdiFactService.processDesAdvAsnEdiFact(interchange, fileUploadParams);
//				batchId = fileUploadParams.getBatchId();
//				break;
//				
//			case "ORDERS":
//				standardInboundOrdersEdifactService.processOrdersEdiFact(interchange, fileUploadParams);
//				break;
//			
//			default:
//				break;
//		}

		log.info("StandardInboundFileProcessService | processEdifactFile- Response : {}", batchId);
		return fileUploadParams;
	}

	public FileUploadParams processXMLFile(String xmlData, FileUploadParams fileUploadParams,
			ProcessExecutionLog processExecutionLog) throws Exception {
		log.info("StandardInboundFileProcessService | processXMLFile - Request : XmlData - " + xmlData
				+ "FileUploadParams - " + fileUploadParams);
		Integer batchId = null;
		FileProcessResponse fileProcessResponse = new FileProcessResponse();

		String documentType = getDocumentType(xmlData);

		fileUploadParams.setEdiType(documentType);
		fileUploadParams.setTrnType(documentType);

		Optional<TransportServiceMapping> optTransportServiceMapping = transportServiceMappingRepository
				.findBySenderPartnerIdAndReceiverPartnerIdAndDirectionAndTransactionType(
						Integer.parseInt(fileUploadParams.getSenderPartnerId()),
						Integer.parseInt(fileUploadParams.getReceiverPartnerId()), DIRECTION.INBOUND.getDirection(),
						documentType);

		if (optTransportServiceMapping.isPresent()) {

			TransportServiceMapping transportServiceMapping = optTransportServiceMapping.get();

			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_IDENTIFIED.getValue() + " : "
							+ transportServiceMapping.getServiceClassName(),
					null);

			log.info("StandardInboundFileProcessService : transportServiceMapping :: "
					+ transportServiceMapping.toString());

			StandardInboundService standardService = (StandardInboundService) applicationContext
					.getBean(transportServiceMapping.getServiceClassName());

			fileUploadParams = standardService.processInboundXMLRequest(xmlData, fileUploadParams);

		} else {
			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue(), null);

			log.info("StandardInboundFileProcessService : transportServiceMapping :: "
					+ TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue());
		}

		log.info("StandardInboundFileProcessService | processXMLFile - Response : {}", batchId);
		return fileUploadParams;
	}

	private String getDocumentType(String xmlData) {
		String documentType = "";
		for (EDIFACTDOCUMENTTYPE value : EDIFACTDOCUMENTTYPE.values()) {
			if (xmlData.contains(value.getText())) {
				documentType = value.getType();
				if (documentType.equalsIgnoreCase(EDIFACTTRANSACTIONTYPE.SHIP_NOTICE.getValue())
						&& (xmlData.contains(forwarded) || xmlData.contains(received))) {
					documentType = EDIFACTTRANSACTIONTYPE.IFTSTA.getValue();
				}
			}
		}
		return documentType;
	}
	
	private String getEDIFACTDocumentType(String ediJson) {
		JSONObject obj = new JSONObject(ediJson);
		String value = "";
		JSONArray interchangesArr = obj.getJSONArray("interchanges");
		JSONObject interchangesObj = interchangesArr.getJSONObject(0);
		JSONArray trnsactionsArr = interchangesObj.getJSONArray("transactions");
		JSONObject transactionObj = trnsactionsArr.getJSONObject(0);
		if(transactionObj.has("UNH_02_MessageIdentifier"))
		{
			JSONObject unh = (JSONObject) transactionObj.get("UNH_02_MessageIdentifier");
			if(unh.has("UNH_02_01_MessageType"))
				value = unh.getString("UNH_02_01_MessageType");
			
		}
//		return interchange.getGroup().getTransactionLst().get(0).getDocType();
		return value;
	}

}
