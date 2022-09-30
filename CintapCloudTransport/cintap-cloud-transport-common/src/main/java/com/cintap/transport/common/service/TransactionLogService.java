package com.cintap.transport.common.service;

import java.time.Instant;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.EDIFACTTRANSACTIONTYPE;
import com.cintap.transport.common.enums.STANDARD;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.AuditLog;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.trans.AuditLogRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionLogService{
	
	@Autowired
	TransactionLogRepository transactionLogRepository;
	
	@Autowired
	AuditLogRepository auditLogRepository;
	
	public void udpateTransactionLogStatus(Integer bpiLogId,String errorMSg,Integer status) {
		Optional<TransactionLog> optTrnLog = transactionLogRepository.findByBpiLogId(bpiLogId);
		if(optTrnLog.isPresent()) {
			TransactionLog trnlog = optTrnLog.get();
			trnlog.setStatusId(status);
			trnlog.setUpdatedDate(TransportCommonUtility.getCurrentDateTime());
			transactionLogRepository.save(trnlog);
			AuditLog auditLog = AuditLog.builder()
					.bpiLogId(trnlog.getBpiLogId())
					.createdDate(TransportCommonUtility.getCurrentDateTime())
					.componentName(COMPONENTNAME.TRANSACTION.getComponentName())
					.componentRecId(trnlog.getBatchId()) //Storing batch id
					.actionType(ACTIONTYPE.UPDATE.getAction())
					.action(errorMSg)
					.createdBy(StringUtils.isNoneEmpty(trnlog.getStpId())?trnlog.getStpId():"0")
					.build();
			auditLogRepository.save(auditLog);
			log.info(bpiLogId+" :: UPDATED TRANSACTION LOG AND AUDIT LOG TABLES "+status+" STATUS :: ");
		}
	}

	/*public TransactionLog saveEdiTransaction(String ediData,FileUploadParams fileUploadParams,TransactionLogParams transactionLogParams,EdiSegments ediSegment) {
		TransactionLog transactionLog = TransactionLog.builder()
				.tpId(fileUploadParams.getPartnerId())
				.isaControlId(ediSegment.getIsaSegment().getIsa13InterchangeControlNumber())
				.isaHeaderRecord(transactionLogParams.getIsaHeader())
				.processType(TRANSACTIONSOURCE.ELECTRONIC.getValue())
				.isaTrailerRecord(transactionLogParams.getIsaTrailer())
				.gsControlId(ediSegment.getGsSegment().getGs06GroupControlNumber())
				.gsHeaderRecord(transactionLogParams.getGsHeader())
				.gsTralierRecord(transactionLogParams.getGsTrailer())
				.stTransactionsRecord(transactionLogParams.getTransactionBuilder())
				.senderIsa(ediSegment.getIsaSegment().getIsa06SenderId().trim())
				.receiverIsa(ediSegment.getIsaSegment().getIsa08ReceiverId().trim())
				.stpTransId(ediTransactionService.fetchTransId(ediSegment))
				.stpSourceId(String.valueOf(ediSegment.getStSegment().getSt01IdentifierCode()))
				.transactionRecord(transactionLogParams.getTransactionBuilder())
				.stpId(fileUploadParams.getSenderPartnerId())
				.rtpId(fileUploadParams.getReceiverPartnerId())
				.rawFile(ediData)
				.ediVersion(fileUploadParams.getEdiVersion())
				.stControlNumber(ediSegment.getStSegment().getSt02ControlNumber())
				.transactionType(fileUploadParams.getTrnType())
				.fileType(fileUploadParams.getActualFileType())
				.batchId(fileUploadParams.getBatchId())
				.partnerProcessDate(ediSegment.getGsSegment()!=null?ediSegment.getGsSegment().getGs04Date():"")
				.refCode(ediTransactionService.fetchRefCode(ediSegment))
				.createdDate(CintapUtility.getCurrentDateTime())
				.createdBy(fileUploadParams.getPartnerId())
				.statusId(CINTAPBPISTATUS.NEW.getStatusId())
				.source(fileUploadParams.getSource())
				.senderDisplayName(fileUploadParams.getSenderPartnerDisplayName())
				.receiverDisplayName(fileUploadParams.getReceiverPartnerDisplayName())
				.build();
		
		TransactionLog trnLog = getTransactionLogRepository().save(transactionLog);
		fileUploadParams.setBpiLogId(trnLog.getBpiLogId());
		appLogger.info(fileUploadParams.getBatchId()+" :: SAVED EDI TRANSACTION DETAILS WITH TRANSACTION ID : "+trnLog.getBpiLogId());
		return trnLog;
	}
	
	public TransactionLog saveTransaction(String ediData,FileUploadParams fileUploadParams,String transId,String refCode,
			String transactionData,String standard) {
		TransactionLog transactionLog = TransactionLog.builder()
				.tpId(fileUploadParams.getPartnerId())
				.processType(TRANSACTIONSOURCE.ELECTRONIC.getValue())
				.senderIsa(fileUploadParams.getSenderIsaId())
				.senderDisplayName(fileUploadParams.getSenderPartnerDisplayName())
				.receiverIsa(fileUploadParams.getReceiverIsaId())
				.receiverDisplayName(fileUploadParams.getReceiverPartnerDisplayName())
				.stpTransId(transId)
				.stpId(fileUploadParams.getSenderPartnerId())
				.rtpId(fileUploadParams.getReceiverPartnerId())
				.stpSourceId(fileUploadParams.getEdiType())
				.transactionRecord(transactionData)
				.rawFile(ediData)
				.fileType(fileUploadParams.getFileType())
				.batchId(fileUploadParams.getBatchId())
				.refCode(refCode)
				.partnerProcessDate(fileUploadParams.getShipmentDate())
				.createdDate(CintapUtility.getCurrentDateTime())
				.createdBy(fileUploadParams.getPartnerId())
				.statusId(CINTAPBPISTATUS.NEW.getStatusId())
				.source(fileUploadParams.getSource())
				.standard(standard)
				.transactionType(fileUploadParams.getTrnType())
				.build();
		
		TransactionLog trnLog = getTransactionLogRepository().save(transactionLog);
		appLogger.info(fileUploadParams.getBatchId()+" :: SAVED FLAT FILE TRANSACTION DETAILS WITH TRANSACTION ID : "+trnLog.toString());
		return trnLog;
	}*/
}