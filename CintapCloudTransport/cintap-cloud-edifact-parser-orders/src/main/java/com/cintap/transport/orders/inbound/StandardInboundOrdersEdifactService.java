package com.cintap.transport.orders.inbound;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.EDIFACTJSONFIELDS;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.StandardInboundService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.TransportCommonBuilder;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;
import com.cintap.transport.orders.parser.OrdersInboundEdifactParser;
import com.cintap.transport.repository.edifact.orders.OrdersHeaderRepository;
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
@Service("StandardInboundOrdersEdifactService")
public class StandardInboundOrdersEdifactService implements StandardInboundService {

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	OrdersInboundEdifactParser ordersInboundEdifactParser;

	@Autowired
	OrdersHeaderRepository ordersHeaderRepository;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;

	@Autowired
	TransportCommonBuilder transportCommonBuilder;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	public FileUploadParams processInboundRequest(String ediJson, FileUploadParams fileUploadParams) {

		log.info("StandardInboundOrdersEdiFactService : Request received");
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
				OrdersHeader ordersHeader = ordersInboundEdifactParser.convertModelToEntity(interchangesObj,
						transactionObj, fileUploadParams);

				if (null == ordersHeader) {
					log.info("StandardInboundOrdersEdiFactService : Unable to convert into Entity");

					transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
							LOGTYPE.ERROR.getValue(), TRANSPORTLOG.MAPPING_FAILED.getValue(), 0);
					return fileUploadParams;
				}

				fileUploadParams.setBgmMessageNumber(ordersHeader.getBgmOrder());
				fileUploadParams.setInterchangeControlRef(ordersHeader.getInterchangeControlRef());
				fileUploadParams.setCreationDate(ordersHeader.getCreationDate());
				fileUploadParams.setSenderCode(ordersHeader.getSenderAddress());
				fileUploadParams.setReceiverCode(ordersHeader.getReceiverAddress());

				TransactionLog transactionLog = transportCommonBuilder.buildTransactionLog(fileUploadParams);

				transactionLog.setRefCode(ordersHeader.getInterchangeControlRef());
				TransactionLog trnLog = transactionLogRepository.save(transactionLog);

				log.info("TRANSACTION SUCCESSFULLY CREATED IN TRANSACTION LOG :: Bpi Log Id : " + trnLog.getBpiLogId());

				Integer bpiLogId = trnLog.getBpiLogId();
				fileUploadParams.setBatchId(trnLog.getBatchId());
				fileUploadParams.setBpiLogId(bpiLogId);

				TransactionLogInboundOutbound transactionLogInboundOutbound = transportCommonBuilder
						.buildTransactionLogInboundOutbound(fileUploadParams);

				transactionLogInboundOutboundRepository.save(transactionLogInboundOutbound);

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						TRANSPORTLOG.FILE_PARSING_SUCCESSFUL.getValue() + bpiLogId, 0);

				bpiLogHeaderRepository.updateBpiLogId(bpiLogId != null ? bpiLogId.intValue() : 0,
						fileUploadParams.getBpiHeaderLogId());

				auditLogService.saveAuditLog(ordersHeader.getSenderPartnerId(), bpiLogId,
						COMPONENTNAME.TRANSACTION.getComponentName(), bpiLogId, ACTIONTYPE.CREATE.getAction(),
						"ORDER INBOUND parsed successfully # " + bpiLogId);

				ordersHeader.setBpiLogId(bpiLogId);
				OrdersHeader ordersHeaderObj = ordersHeaderRepository.save(ordersHeader);

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						TRANSPORTLOG.TRANSACTION_CREATED.getValue() + " : " + ordersHeaderObj.getId(), 0);

				log.info("StandardInboundOrdersEdiFactService : Order Header : "
						+ TransportCommonUtility.convertObjectToJson(ordersHeaderObj) + " Response : "
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
