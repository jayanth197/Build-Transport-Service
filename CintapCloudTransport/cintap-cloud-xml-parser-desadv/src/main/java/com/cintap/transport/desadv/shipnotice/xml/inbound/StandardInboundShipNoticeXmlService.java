package com.cintap.transport.desadv.shipnotice.xml.inbound;

import java.util.Optional;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.StandardInboundService;
import com.cintap.transport.common.service.TransactionTrailService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.TransportCommonBuilder;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.desadv.shipnotice.xml.parser.DespatchAdviceShipNoticeInboundXmlParser;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeaderReference;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;
import com.cintap.transport.repository.common.PartnerPlantRepository;
import com.cintap.transport.repository.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeaderRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("StandardInboundShipNoticeXmlService")
public class StandardInboundShipNoticeXmlService implements StandardInboundService {

	@Autowired
	DespatchAdviceShipNoticeInboundXmlParser despatchAdviceShipNoticeInboundXmlParser;

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	DespatchAdviceShipNoticeHeaderRepository despatchAdviceShipNoticeHeaderRepository;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;

	@Autowired
	TransportCommonBuilder transportCommonBuilder;

	@Autowired
	TransactionTrailService transactionTrailService;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	@Autowired
	PartnerPlantRepository partnerPlantRepository;

	@Override
	public FileUploadParams processInboundXMLRequest(String fileData, FileUploadParams fileUploadParams)
			throws Exception {

		log.info("StandardInboundDesAdvAsnEdiFactService : Request received");
		try {
			// Map data from interchange to Database entity
			DespatchAdviceShipNoticeHeader header = despatchAdviceShipNoticeInboundXmlParser
					.convertXmlToEntity(fileData, fileUploadParams);

			if (null == header) {
				log.info("StandardInboundDesAdvAsnEdiFactService : Unable to convert into Entity");

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
						TRANSPORTLOG.MAPPING_FAILED.getValue(), 0);
				return fileUploadParams;
			}

			fileUploadParams.setAckType(header.getLstDesadvShipNoitceShipment().get(0).getStatus());

			fileUploadParams.setBgmMessageNumber(header.getLstDesadvShipNoitceShipment().get(0).getIdentification());
			fileUploadParams.setSenderCode(getSourceCode(header, fileUploadParams));
			fileUploadParams
					.setReceiverCode(getPlantCode(fileUploadParams.getReceiverPartnerId(), header.getReceiverCode()));
			TransactionLog transactionLog = transportCommonBuilder.buildTransactionLog(fileUploadParams);

			transactionLog.setRefCode(header.getMessageReference());
			TransactionLog trnLog = transactionLogRepository.save(transactionLog);

			Integer bpiLogId = trnLog.getBpiLogId();

			fileUploadParams.setBatchId(trnLog.getBatchId());
			fileUploadParams.setBpiLogId(bpiLogId);
			fileUploadParams.setRawFile(fileData);

			String parentTransactionId = "";
			transactionTrailService.buildTransactionTrail(transactionLog, parentTransactionId);

			TransactionLogInboundOutbound transactionLogInboundOutbound = transportCommonBuilder
					.buildTransactionLogInboundOutbound(fileUploadParams);

			transactionLogInboundOutboundRepository.save(transactionLogInboundOutbound);

			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.FILE_PARSING_SUCCESSFUL.getValue() + bpiLogId, 0);

			bpiLogHeaderRepository.updateBpiLogId(trnLog.getBpiLogId() != null ? trnLog.getBpiLogId().intValue() : 0,
					fileUploadParams.getBpiHeaderLogId());

			auditLogService.saveAuditLog(fileUploadParams.getSenderPartnerId(), bpiLogId,
					COMPONENTNAME.TRANSACTION.getComponentName(), bpiLogId, ACTIONTYPE.CREATE.getAction(),
					"ShipNotice Manifest -" + fileUploadParams.getAckType() + " INBOUND EDIFACT parsed successfully # "
							+ bpiLogId);

			header.setBpiLogId(bpiLogId);
			DespatchAdviceShipNoticeHeader shipNoticeHeader = despatchAdviceShipNoticeHeaderRepository.save(header);

			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.TRANSACTION_CREATED.getValue() + " : " + shipNoticeHeader.getId(), 0);

			log.info("StandardInboundDesAdvAsnEdiFactService : Despatch Advice Header : "
					+ TransportCommonUtility.convertObjectToJson(shipNoticeHeader) + " Response : "
					+ TransportCommonUtility.convertObjectToJson(fileUploadParams));
		} catch (Exception e) {
			log.info("Exception is : " + e);
			String msg = e.getMessage();
			log.info(fileUploadParams.getBatchId() + " :: Failed processing EDI file :: " + msg);
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());

			if (e instanceof DataIntegrityViolationException) {
				msg = "Duplicate Transaction";
				/**
				 * Reject transaction in the case of Duplicate Order
				 */

			}

			fileUploadParams.setTransactionErrorReason(msg);
			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(), msg,
					0);
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());
			transportCommonService.updateBpiHeader(fileUploadParams);
		}
		return fileUploadParams;
	}

	private String getSourceCode(DespatchAdviceShipNoticeHeader header, FileUploadParams fileUploadParams) {
		String name = "";
		for (DespatchAdviceShipNoticeHeaderReference headerReference : header.getLstDesadvShipNoitceHeaderReference()) {
			if ("Source".equals(headerReference.getKey())) {
				name = getPlantCode(fileUploadParams.getSenderPartnerId(), headerReference.getValue());
				break;
			}
		}
		return name;
	}

	private String getPlantCode(String partnerId, String code) {
		String name = "";
		Optional<String> optPartnerPlants = partnerPlantRepository.findNameByPartnerIdAndPartnerCode(partnerId, code);
		if (optPartnerPlants.isPresent()) {
			name = optPartnerPlants.get();
		}
		return name;
	}

}
