package com.cintap.transport.inbound;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.EDIFACTJSONFIELDS;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.StandardInboundService;
import com.cintap.transport.common.service.TransactionTrailService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.TransportCommonBuilder;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;
import com.cintap.transport.parser.DespatchAdviceInboundEdifactParser;
import com.cintap.transport.repository.edifact.desadv.DespatchAdviceHeaderRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("StandardInboundDesAdvAsnEdiFactService")
public class StandardInboundDesAdvAsnEdiFactService implements StandardInboundService {

	@Autowired
	DespatchAdviceInboundEdifactParser despatchAdviceInboundEdifactParser;

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	DespatchAdviceHeaderRepository despatchAdviceHeaderRepository;

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

	public FileUploadParams processInboundRequest(String ediJson, FileUploadParams fileUploadParams) {

		log.info("StandardInboundDesAdvAsnEdiFactService : Request received");

		try {
			JSONObject obj = new JSONObject(ediJson);
			JSONArray interchangesArr = obj.getJSONArray(EDIFACTJSONFIELDS.INTERCHANGES.getAction());
			JSONObject interchangesObj = interchangesArr.getJSONObject(0);
			JSONArray trnsactionsArr = interchangesObj.getJSONArray(EDIFACTJSONFIELDS.TRANSACTIONS.getAction());
			int trnLength = trnsactionsArr.length();
			for (int i = 0; i < trnLength; i++) {
				if (i > 0)
					fileUploadParams = transportCommonService.dumpLogDetails(fileUploadParams);
				// Map data from interchange to Database entity
				JSONObject transactionObj = trnsactionsArr.getJSONObject(i);

				DespatchAdviceHeader despatchAdviceHeader = despatchAdviceInboundEdifactParser
						.convertModelToEntity(interchangesObj, transactionObj, fileUploadParams);

				if (null == despatchAdviceHeader) {
					log.info("StandardInboundDesAdvAsnEdiFactService : Unable to convert into Entity");

					transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
							LOGTYPE.ERROR.getValue(), TRANSPORTLOG.MAPPING_FAILED.getValue(), 0);
					return fileUploadParams;
				}

				fileUploadParams.setBgmMessageNumber(despatchAdviceHeader.getBgmDocMsgNumber());
				fileUploadParams.setInterchangeControlRef(despatchAdviceHeader.getInterchangeControlRef());
				fileUploadParams.setCreationDate(despatchAdviceHeader.getCreationDate());
				fileUploadParams.setSenderCode(despatchAdviceHeader.getSenderAddress());
				fileUploadParams.setReceiverCode(despatchAdviceHeader.getReceiverAddress());

				TransactionLog transactionLog = transportCommonBuilder.buildTransactionLog(fileUploadParams);

				transactionLog.setRefCode(despatchAdviceHeader.getInterchangeControlRef());
				TransactionLog trnLog = transactionLogRepository.save(transactionLog);
				Integer bpiLogId = trnLog.getBpiLogId();

				fileUploadParams.setBatchId(trnLog.getBatchId());
				fileUploadParams.setBpiLogId(bpiLogId);

				String parentTransactionId = despatchAdviceHeader.getBgmDocMsgNumber();
				transactionTrailService.buildTransactionTrail(transactionLog, parentTransactionId);

				TransactionLogInboundOutbound transactionLogInboundOutbound = transportCommonBuilder
						.buildTransactionLogInboundOutbound(fileUploadParams);

				transactionLogInboundOutboundRepository.save(transactionLogInboundOutbound);

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						TRANSPORTLOG.FILE_PARSING_SUCCESSFUL.getValue() + bpiLogId, 0);

				bpiLogHeaderRepository.updateBpiLogId(
						trnLog.getBpiLogId() != null ? trnLog.getBpiLogId().intValue() : 0,
						fileUploadParams.getBpiHeaderLogId());

				auditLogService.saveAuditLog(despatchAdviceHeader.getSenderPartnerId(), bpiLogId,
						COMPONENTNAME.TRANSACTION.getComponentName(), bpiLogId, ACTIONTYPE.CREATE.getAction(),
						"DESADV ASN INBOUND EDIFACT parsed successfully # " + bpiLogId);

				despatchAdviceHeader.setBpiLogId(bpiLogId);
				DespatchAdviceHeader despatchAdviceHeaderObj = despatchAdviceHeaderRepository
						.save(despatchAdviceHeader);

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						TRANSPORTLOG.TRANSACTION_CREATED.getValue() + " : " + despatchAdviceHeaderObj.getId(), 0);

				log.info("StandardInboundDesAdvAsnEdiFactService : Despatch Advice Header : "
						+ TransportCommonUtility.convertObjectToJson(despatchAdviceHeaderObj) + " Response : "
						+ TransportCommonUtility.convertObjectToJson(fileUploadParams));
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
