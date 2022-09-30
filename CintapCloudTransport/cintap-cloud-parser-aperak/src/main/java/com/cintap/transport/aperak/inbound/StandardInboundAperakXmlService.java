package com.cintap.transport.aperak.inbound;

import java.util.Optional;

import com.cintap.transport.aperak.outbound.StandardOutboundAperakService;
import com.cintap.transport.aperak.xml.parser.AperakXmlToEdifactParser;
import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.StandardInboundService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.entity.trans.BpiLogHeader;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("StandardInboundAperakXmlService")
public class StandardInboundAperakXmlService implements StandardInboundService {

	@Autowired
	AperakXmlToEdifactParser aperakInboundEdifactParser;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;

	@Autowired
	StandardOutboundAperakService standardOutboundAperakEdifactService;

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Override
	public FileUploadParams processInboundXMLRequest(String fileData, FileUploadParams fileUploadParams) {

		log.info("StandardInboundOrdersEdiFactService : Request received");
		try {
			// Map data from interchange to Database entity
			fileUploadParams = aperakInboundEdifactParser.convertModelToEntity(fileData, fileUploadParams);

			if (null == fileUploadParams.getBpiLogId()) {
				log.info("StandardInboundOrdersEdiFactService : Unable to convert into Entity");

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
						TRANSPORTLOG.MAPPING_FAILED.getValue(), 0);
				return fileUploadParams;
			}
			Integer bpiLogId = fileUploadParams.getBpiLogId();

			TransactionLogInboundOutbound transactionLogInboundOutbound = TransactionLogInboundOutbound.builder()
					.bpiLogId(bpiLogId).fileName(fileUploadParams.getFileName())
					.fileType(fileUploadParams.getFileType()).ackType(fileUploadParams.getAckType())
					.ackRefNumber(fileUploadParams.getAckRefNum()).receivedRawFile(fileData)
					.sentRawFile(fileUploadParams.getOutboundRawFile()).transactionType(fileUploadParams.getTrnType())
					.isSent(false).build();

			transactionLogInboundOutboundRepository.save(transactionLogInboundOutbound);

			log.info("StandardInboundOrdersEdiFactService : Transaction Log Inbound Outbound "
					+ transactionLogInboundOutbound);
			BpiLogHeader bpiLogHeader = null;
			Optional<BpiLogHeader> optBpiLogHeader = bpiLogHeaderRepository
					.findByBpiLogIdAndDirection(fileUploadParams.getBpiLogId(), fileUploadParams.getDirection());
			if (optBpiLogHeader.isPresent()) {
				bpiLogHeader = optBpiLogHeader.get();

				fileUploadParams.setGroupId(bpiLogHeader.getGroupId());
				fileUploadParams.setBpiHeaderLogId(bpiLogHeader.getLogHdrId());
				bpiLogHeaderRepository.updateBpiLogId(bpiLogId, fileUploadParams.getBpiHeaderLogId());

				auditLogService.saveAuditLog(fileUploadParams.getSenderPartnerId(), bpiLogId,
						COMPONENTNAME.TRANSACTION.getComponentName(), bpiLogId, ACTIONTYPE.CREATE.getAction(),
						"APERAK parsed successfully # " + bpiLogId);

				BpiLogDetail bpiLogDetail = transportCommonService.getBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
						LOGTYPE.INFO.getValue(), TRANSPORTLOG.TRANSACTION_CREATED.getValue()
								+ " transactionLogInboundOutbound: " + transactionLogInboundOutbound.getId(),
						0);
				fileUploadParams.getLstLogDetails().add(bpiLogDetail);

				transportCommonService.saveBpiLogDetailAperak(fileUploadParams);

				Integer ackStatus = TransportCommonUtility.getAckStatus(fileUploadParams.getAckType());

				transactionLogRepository.updateAckStatus(bpiLogId, ackStatus);
			}
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

}
