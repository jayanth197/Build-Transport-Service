package com.cintap.transport.iftman.inbound;

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
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceHeader;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceReceipt;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;
import com.cintap.transport.iftman.parser.ReceivingAdviceInboundXmlParser;
import com.cintap.transport.repository.common.PartnerPlantRepository;
import com.cintap.transport.repository.edifact.iftman.ReceivingAdviceHeaderRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("StandardInboundReceivingAdviceXmlService")
public class StandardInboundReceivingAdviceXmlService implements StandardInboundService {

	@Autowired
	ReceivingAdviceInboundXmlParser receivingAdviceInboundXmlParser;

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	ReceivingAdviceHeaderRepository receivingAdviceHeaderRepository;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;

	@Autowired
	TransportCommonBuilder transportCommonBuilder;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	@Autowired
	TransactionTrailService transactionTrailService;

	@Autowired
	PartnerPlantRepository partnerPlantRepository;

	@Override
	public FileUploadParams processInboundXMLRequest(String fileData, FileUploadParams fileUploadParams)
			throws Exception {
		log.info("StandardInboundDesAdvAsnEdiFactService : Request received");
		try {
			// Map data from interchange to Database entity
			ReceivingAdviceHeader header = receivingAdviceInboundXmlParser.convertXmlToEntity(fileData,
					fileUploadParams);

			if (null == header) {
				log.info("StandardInboundDesAdvAsnEdiFactService : Unable to convert into Entity");

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
						TRANSPORTLOG.MAPPING_FAILED.getValue(), 0);
				return fileUploadParams;
			}

			fileUploadParams.setBgmMessageNumber(header.getLstReceivingAdviceReceipt().get(0).getIdentification());
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

			String parentTransactionId = header.getLstReceivingAdviceReceipt().get(0).getIdentification();
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
					"IFTSTA INBOUND EDIFACT parsed successfully # " + bpiLogId);

			header.setBpiLogId(bpiLogId);
			ReceivingAdviceHeader adviceHeader = receivingAdviceHeaderRepository.save(header);

			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					TRANSPORTLOG.TRANSACTION_CREATED.getValue() + " : " + adviceHeader.getId(), 0);

			log.info("StandardInboundDesAdvAsnEdiFactService : Despatch Advice Header : "
					+ TransportCommonUtility.convertObjectToJson(adviceHeader) + " Response : "
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

	private String getSourceCode(ReceivingAdviceHeader header, FileUploadParams fileUploadParams) {
		String code = "";
		ReceivingAdviceReceipt receipt = header.getLstReceivingAdviceReceipt().get(0);
		code = getPlantCode(fileUploadParams.getSenderPartnerId(), receipt.getVendorCode());
		return code;
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
