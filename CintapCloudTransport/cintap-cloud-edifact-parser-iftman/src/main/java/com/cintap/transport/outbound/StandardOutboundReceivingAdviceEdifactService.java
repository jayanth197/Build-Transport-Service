package com.cintap.transport.outbound;

import java.util.Optional;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.StandardOutboundService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.TransportCommonBuilder;
import com.cintap.transport.entity.common.TransportServiceMapping;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceHeader;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.parser.ReceivingAdviceOutboundEdifactParser;
import com.cintap.transport.repository.edifact.iftman.ReceivingAdviceHeaderRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("StandardOutboundReceivingAdviceEdifactService")
public class StandardOutboundReceivingAdviceEdifactService implements StandardOutboundService {

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
	ReceivingAdviceOutboundEdifactParser receivingAdviceOutboundEdifactParser;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	public String processOutboundRequest(Integer bpiLogId, FileUploadParams fileUploadParams,
			TransportServiceMapping transportServiceMapping) throws Exception {

		log.info("StandardInboundDesAdvAsnEdiFactService : Request received");

		String edifactData = "";
		try {
			Optional<ReceivingAdviceHeader> optReceivingAdviceHeader = receivingAdviceHeaderRepository
					.findByBpiLogId(bpiLogId);

			if (optReceivingAdviceHeader.isPresent()) {
				Optional<TransactionLog> optTransactionLog = transactionLogRepository.findByBpiLogId(bpiLogId);
				if (optTransactionLog.isPresent()) {
					TransactionLog transactionLog = optTransactionLog.get();
					edifactData = receivingAdviceOutboundEdifactParser.generateOutbound(optReceivingAdviceHeader.get(),
							transactionLog, fileUploadParams);
					transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
							LOGTYPE.INFO.getValue(), TRANSPORTLOG.INITIATED_EDIFACT_CONVERTION.getValue(), 0);

					auditLogService.saveAuditLog(fileUploadParams.getSenderPartnerId(), bpiLogId,
							COMPONENTNAME.TRANSACTION.getComponentName(), bpiLogId, ACTIONTYPE.CREATE.getAction(),
							"IFTMAN OUTBOUND EDIFACT generated successfully # " + bpiLogId);
				}
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
		return edifactData;
	}

}
