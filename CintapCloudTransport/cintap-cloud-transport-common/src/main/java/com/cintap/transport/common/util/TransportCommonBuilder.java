
package com.cintap.transport.common.util;

import java.time.Instant;

import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.EDIFACTTRANSACTIONTYPE;
import com.cintap.transport.common.enums.STANDARD;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransportCommonBuilder {


	public TransactionLog buildTransactionLog(FileUploadParams fileUploadParams) {

		log.info("TransportCommonBuilder : Buidling Transaction Log : request : "+fileUploadParams);
		
		TransactionLog transactionLog = TransactionLog.builder()
				.batchId(fileUploadParams.getBatchId())
				.processType(TRANSACTIONSOURCE.ELECTRONIC.getValue())
				.senderPartnerCode(fileUploadParams.getSenderCode())
				.receiverPartnerCode(fileUploadParams.getReceiverCode())
				.senderIsa(fileUploadParams.getSenderIsaId())
				.receiverIsa(fileUploadParams.getReceiverIsaId())
				.stpTransId(fileUploadParams.getBgmMessageNumber())
				.stpSourceId("" + fileUploadParams.getTransType())
				.stpId(fileUploadParams.getSenderPartnerId())
				.rtpId(fileUploadParams.getReceiverPartnerId())
				.ediVersion(fileUploadParams.getEdiVersion())
				.stControlNumber(fileUploadParams.getInterchangeControlRef())
				.transactionType(null != fileUploadParams.getTrnType() ? fileUploadParams.getTrnType().toUpperCase() : "")
				.fileType(fileUploadParams.getActualFileType()).batchId(fileUploadParams.getBatchId())
				.partnerProcessDate(fileUploadParams.getCreationDate()).createdDate(Instant.now().toString())
				.createdBy(fileUploadParams.getPartnerId()).statusId(CINTAPBPISTATUS.NEW.getStatusId())
				.source(fileUploadParams.getSource())
				.standard(fileUploadParams.getFileType())
				//.createdDate(TransportCommonUtility.getCurrentDateTime())
				.build();
		
		log.info("TransportCommonBuilder : Buidling Transaction Log : response : "+transactionLog);
		return transactionLog;
	}

	public TransactionLogInboundOutbound buildTransactionLogInboundOutbound(FileUploadParams fileUploadParams) {
		log.info("TransportCommonBuilder : Buidling Transaction Log Inbound Outbound : request : "+fileUploadParams);
		
		TransactionLogInboundOutbound transactionLogInboundOutbound = TransactionLogInboundOutbound.builder()
				.bpiLogId(fileUploadParams.getBpiLogId())
				.fileType(fileUploadParams.getFileType())
				.fileName(fileUploadParams.getFileName())
				.receivedRawFile(fileUploadParams.getRawFile())
				.transactionType(fileUploadParams.getTrnType())
				.ackType(fileUploadParams.getAckType())
				.build();
		
		log.info("TransportCommonBuilder : Buidling Transaction Log Inbound Outbound : response : "+transactionLogInboundOutbound);
		return transactionLogInboundOutbound;
	}

}
