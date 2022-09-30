package com.cintap.transport.parsing.service;

import java.util.Optional;

import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.CommonServiceImpl;
import com.cintap.transport.common.service.StandardOutboundService;
import com.cintap.transport.desadv.xml.outbound.StandardOutboundDesAdvAsnXmlService;
import com.cintap.transport.entity.common.ProcessExecutionLog;
import com.cintap.transport.entity.common.TransportServiceMapping;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.message.model.FileProcessResponse;
import com.cintap.transport.repository.common.TransportServiceMappingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StandardOutboundFileProcessService {

	@Autowired
	StandardOutboundDesAdvAsnXmlService standardOutboundDesAdvAsnXmlService;

	@Autowired
	CommonServiceImpl commonServiceImpl;

	@Autowired
	TransportServiceMappingRepository transportServiceMappingRepository;

	@Autowired
	ApplicationContext applicationContext;

	public FileProcessResponse processEdiFile(String ediData, FileUploadParams fileUploadParams,
			ProcessExecutionLog processExecutionLog) {
		log.info("StandardOutboundFileProcessService | processEdiFile - Request : EdiData - " + ediData
				+ "FileUploadParams - " + fileUploadParams);
		Integer batchId = null;
		FileProcessResponse fileProcessResponse = new FileProcessResponse();

		log.info("StandardOutboundFileProcessService | processEdiFile- Response : {}", batchId);
		return fileProcessResponse;
	}

	public String processEdifactFile(TransactionLog transactionLog, FileUploadParams fileUploadParams) throws Exception {
		log.info("StandardOutboundFileProcessService | processEdifactFile - Request : TransactionLog - "
				+ transactionLog + "FileUploadParams - " + fileUploadParams);
		Integer batchId = null;
		FileProcessResponse fileProcessResponse = new FileProcessResponse();
		String edifactData = "";
		
		Optional<TransportServiceMapping> optTransportServiceMapping = transportServiceMappingRepository
				.findBySenderPartnerIdAndReceiverPartnerIdAndDirectionAndTransactionType(
						Integer.parseInt(fileUploadParams.getSenderPartnerId()),
						Integer.parseInt(fileUploadParams.getReceiverPartnerId()), DIRECTION.OUTBOUND.getDirection(),
						fileUploadParams.getEdiType());

		if (optTransportServiceMapping.isPresent()) {

			TransportServiceMapping transportServiceMapping = optTransportServiceMapping.get();

			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_IDENTIFIED.getValue() + " : "
							+ transportServiceMapping.getServiceClassName(),
					null);

			log.info("StandardOutboundFileProcessService : transportServiceMapping :: "
					+ transportServiceMapping.toString());

			StandardOutboundService standardService = (StandardOutboundService) applicationContext
					.getBean(transportServiceMapping.getServiceClassName());

			edifactData = standardService.processOutboundRequest(transactionLog.getBpiLogId(), fileUploadParams, transportServiceMapping);

		} else {
			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue(), null);

			log.info("StandardOutboundFileProcessService : transportServiceMapping :: "
					+ TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue());
		}

		log.info("StandardOutboundFileProcessService | processEdifactFile- Response : {}", batchId);
		return edifactData;
	}

	public String processXMLFile(TransactionLog transactionLog, FileUploadParams fileUploadParams) throws Exception {
		log.info("StandardOutboundFileProcessService | processXMLFile - Request :" + "TransactionLog - "
				+ transactionLog);
		Integer batchId = null;
		String xmldata = null;

		Optional<TransportServiceMapping> optTransportServiceMapping = transportServiceMappingRepository
				.findBySenderPartnerIdAndReceiverPartnerIdAndDirectionAndTransactionType(
						Integer.parseInt(fileUploadParams.getSenderPartnerId()),
						Integer.parseInt(fileUploadParams.getReceiverPartnerId()), DIRECTION.OUTBOUND.getDirection(),
						fileUploadParams.getEdiType());

		if (optTransportServiceMapping.isPresent()) {

			TransportServiceMapping transportServiceMapping = optTransportServiceMapping.get();

			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_IDENTIFIED.getValue() + " : "
							+ transportServiceMapping.getServiceClassName(),
					null);

			log.info("StandardOutboundFileProcessService : transportServiceMapping :: "
					+ transportServiceMapping.toString());

			StandardOutboundService standardService = (StandardOutboundService) applicationContext
					.getBean(transportServiceMapping.getServiceClassName());

			xmldata = standardService.processOutboundRequest(transactionLog.getBpiLogId(), fileUploadParams, transportServiceMapping);

		} else {
			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue(), null);

			log.info("StandardOutboundFileProcessService : transportServiceMapping :: "
					+ TRANSPORTLOG.SERVICE_CLASS_CONFIG_MISSING.getValue());
		}

		log.info("StandardOutboundFileProcessService | processXMLFile - Response : {}", batchId);
		return xmldata;
	}

}
