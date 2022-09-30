/**
 * 
 */
package com.cintap.transport.common.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.EDIPROCESSTYPE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.AuditLog;
import com.cintap.transport.entity.common.BatchSequenceId;
import com.cintap.transport.entity.common.FileExecutionSequence;
import com.cintap.transport.entity.common.GroupSequenceId;
import com.cintap.transport.entity.common.ProcessExecutionSequence;
import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.entity.trans.BpiLogHeader;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.common.BatchSeqIdRepository;
import com.cintap.transport.repository.common.FileExecutionSequenceRepository;
import com.cintap.transport.repository.common.GroupSeqIdRepository;
import com.cintap.transport.repository.common.ProcessExecutionSequenceRepository;
import com.cintap.transport.repository.trans.AuditLogRepository;
import com.cintap.transport.repository.trans.BatchDetailsRepository;
import com.cintap.transport.repository.trans.BatchJobLog;
import com.cintap.transport.repository.trans.BpiLogDetailRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class CommonServiceImpl {
	
	@Autowired
	private ProcessExecutionSequenceRepository processExecutionSequenceRepository;
	
	@Autowired
	private FileExecutionSequenceRepository fileExecutionSequenceRepository;
	
	@Autowired
	private  BatchDetailsRepository batchDetailsRepository;
	
	@Autowired
	private BpiLogDetailRepository bpiLogDetailRepository;
	
	@Autowired
	private BpiLogHeaderRepository bpiLogHeaderRepository; 
	
	@Autowired
	private GroupSeqIdRepository groupSeqIdRepository;
	
	@Autowired
	private BatchSeqIdRepository batchSeqIdRepository;
	
	@Autowired
	private TransactionLogRepository transactionLogRepository;
	
	@Autowired
	private AuditLogRepository auditLogRepository;
	
	
	
	public Integer getProcessExecutionId() {
		Timestamp ts = new Timestamp(new Date().getTime());
		ProcessExecutionSequence seq = ProcessExecutionSequence.builder()
				.createdBy("SYSTEM")
				.createdDate(ts)
				.updatedBy("SYSTEM")
				.updatedDate(ts)
				.build();
		seq = processExecutionSequenceRepository.save(seq);
		log.info("getProcessExecutionId is : "+seq.getProcessExecutionId());
		return seq.getProcessExecutionId();
	}
	
	public Integer getFileExecutionId() {
		Timestamp ts = new Timestamp(new Date().getTime());
		FileExecutionSequence seq = FileExecutionSequence.builder()
				.createdBy("SYSTEM")
				.createdDate(ts)
				.updatedBy("SYSTEM")
				.updatedDate(ts)
				.build();
		seq = fileExecutionSequenceRepository.save(seq);
		log.info("getProcessExecutionId is : "+seq.getFileExecutionId());
		return seq.getFileExecutionId();
	}
	
	public BpiLogDetail buildBpiLogDetail(String processId,String logType,String logDetails) {
		return BpiLogDetail.builder()
				//.logHeaderId(logHeaderId)
				.processId(Integer.parseInt(processId))
				.logType(logType)
				.logDetails(logDetails)
				.createdDate(TransportCommonUtility.getCurrentDateTime())
				.build();
	}
	
	public void saveBpiLogDetail(Integer logHdrId,String logType,String logDetails,Integer processId) {
		try {
			BpiLogDetail logDetail = BpiLogDetail.builder()
					.logHeaderId(logHdrId)
					.processId(processId)
					.logType(logType)
					.logDetails(logDetails)
					.createdDate(TransportCommonUtility.getCurrentDateTime())
					.build();
			bpiLogDetailRepository.save(logDetail);
		}catch (Exception e) {
			log.error("Got Exception during saving into BpiLog Detail : "+e);
		}
	}

	public BpiLogHeader updateBpiHeader(FileUploadParams fileUploadParams) {
		Optional<BpiLogHeader> optBpiLogHeader;
		BpiLogHeader logHeader=null;
		try {
			if(fileUploadParams.getBpiHeaderLogId()!=null) {
				optBpiLogHeader = bpiLogHeaderRepository.findById(fileUploadParams.getBpiHeaderLogId());
				if(optBpiLogHeader.isPresent()) {
					logHeader = optBpiLogHeader.get(); 
					logHeader.setRawFile(fileUploadParams.getRawFile());
					logHeader.setSenderPtnrDispName(fileUploadParams.getSenderPartnerDisplayName());
					logHeader.setReceiverPtnrDispName(fileUploadParams.getReceiverPartnerDisplayName());
					logHeader.setBatchStatus(fileUploadParams.getBatchStatus());
					logHeader.setNoOfTransactions(fileUploadParams.getTxnCount());
					logHeader.setUpdatedDate(TransportCommonUtility.getCurrentDateTime());
					logHeader.setEndDate(TransportCommonUtility.getCurrentDateTime());
					logHeader.setBpiLogId(fileUploadParams.getBpiLogId()!=null?fileUploadParams.getBpiLogId().intValue():0);
					logHeader=bpiLogHeaderRepository.save(logHeader);
				}
			}
		}catch (Exception e) {
			log.error("Got Exception during Update into BpiLog Header : "+e);
		}
		return logHeader;
	}
	
	public BpiLogHeader getBpiHeaderForAperak (FileUploadParams fileUploadParams) {
		Optional<BpiLogHeader> optBpiLogHeader = bpiLogHeaderRepository.findByBpiLogIdAndDirection(fileUploadParams.getBpiLogId(), fileUploadParams.getDirection());
		if(optBpiLogHeader.isPresent()) {
			return optBpiLogHeader.get();
		}
		return null;
	}

	public BpiLogHeader saveBpiHeader(FileUploadParams fileUploadParams) {
		try {
			BpiLogHeader pfLogHeader = BpiLogHeader.builder()
					.groupId(generateGroupId(fileUploadParams.getGroupId(),fileUploadParams.getFileName()))
					.batchId(generateBatchId(fileUploadParams.getBatchId(),fileUploadParams.getFileName()))
					.processFlowId(fileUploadParams.getProcessFlowId())
					.type(fileUploadParams.getFileType())
					.senderPartnerId(fileUploadParams.getSenderPartnerId()!=null?Integer.parseInt(fileUploadParams.getSenderPartnerId()):0)
					.receiverPartnerId(fileUploadParams.getReceiverPartnerId()!=null?Integer.parseInt(fileUploadParams.getReceiverPartnerId()):0)
					.senderPtnrDispName(fileUploadParams.getSenderPartnerDisplayName()!=null?fileUploadParams.getSenderPartnerDisplayName():"")
					.receiverPtnrDispName(fileUploadParams.getReceiverPartnerDisplayName()!=null?fileUploadParams.getReceiverPartnerDisplayName():"")
					.fileCount(fileUploadParams.getFileCount())
					.fileName(fileUploadParams.getFileName())
					.batchStatus(EDIPROCESSTYPE.INITIATED.getValue())
					.noOfTransactions(0)
					.rawFile(fileUploadParams.getRawFile())
					.createdBy(fileUploadParams.getSenderPartnerId()!=null?fileUploadParams.getSenderPartnerId():"")
					.createdDate(TransportCommonUtility.getCurrentDateTime())
					.direction(fileUploadParams.getDirection())	
					.bpiLogId(fileUploadParams.getBpiLogId())
					.build();
			pfLogHeader=bpiLogHeaderRepository.save(pfLogHeader);
			log.info("pfLogHeader saved is : "+pfLogHeader);
			return pfLogHeader;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Got Exception during saving into BpiLog Header : "+e);
			return null;
		}
	}

	/**
	 * Generating GroupID from database Sequence
	 * @param groupId
	 * @param fileName
	 * @return
	 */
	private Integer generateGroupId(Integer groupId,String fileName) {
		if(groupId==null) {
			GroupSequenceId groupSeqId = groupSeqIdRepository.save(GroupSequenceId.builder().fileName(fileName).build());
			groupId = groupSeqId.getGroupId();	
		}
		return groupId;
	}

	/**
	 * Generating BatchId from database sequence
	 * @param batchId
	 * @param fileName
	 * @return
	 */
	private Integer generateBatchId(Integer batchId,String fileName) {
		if(batchId==null) {
			BatchSequenceId batchSeqId = batchSeqIdRepository.save(BatchSequenceId.builder().fileName(fileName).build());
			batchId = batchSeqId.getBatchId();
		}
		return batchId;
	}

	public String convertExceptionToString(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	public void udpateBatchTransactionLogStatus(Integer batchId,Integer bpiLogId,String message,Integer statusId,
			boolean isUpdate) {
		Optional<BatchJobLog> batchDetailsOpt = batchDetailsRepository.findById(batchId);
		if(batchDetailsOpt.isPresent()) {
			//Batch details table update with failure status
			BatchJobLog batchDetailsObj = batchDetailsOpt.get();
			if(CINTAPBPISTATUS.NEW.getStatusId()==statusId) {
				batchDetailsObj.setBatchStatus(CINTAPBPISTATUS.NEW.getStatusId()+"-"+CINTAPBPISTATUS.NEW.getStatusValue());	
			}else if(CINTAPBPISTATUS.ERROR.getStatusId()==statusId) {
				batchDetailsObj.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusId()+"-"+CINTAPBPISTATUS.ERROR.getStatusValue());
			}else if(CINTAPBPISTATUS.PROCESSED.getStatusId()==statusId){
				batchDetailsObj.setBatchStatus(CINTAPBPISTATUS.PROCESSED.getStatusId()+"-"+CINTAPBPISTATUS.PROCESSED.getStatusValue());
			}
			batchDetailsObj.setEndTime(TransportCommonUtility.getCurrentDateTime());
			batchDetailsObj.setBatchLog(message);
			batchDetailsRepository.save(batchDetailsObj);
		}
		//bpiLogId is generating from sequence but not from the Batch Job table so needs to be updated transaction log independent of it 
		if(isUpdate) {
			udpateTransactionLogStatus(bpiLogId,message,statusId);
		}
	}
	
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
					.actionType(ACTIONTYPE.CREATE.getAction())
					.action(errorMSg)
					.createdBy(StringUtils.isNoneEmpty(trnlog.getStpId())?trnlog.getStpId():"0")
					.build();
			auditLogRepository.save(auditLog);
			log.info(bpiLogId+" :: UPDATED TRANSACTION LOG AND AUDIT LOG TABLES "+status+" STATUS :: ");
		}
	}
	
	public FileUploadParams validateInboundS2696File(String ediFileData,FileUploadParams fileUploadParams) {
		fileUploadParams.setBatchStatus(EDIPROCESSTYPE.INITIATED.getValue());
		return fileUploadParams;
	}

	public FileUploadParams validateInboundAdientFile(String ediFileData,FileUploadParams fileUploadParams) {
		fileUploadParams.setBatchStatus(EDIPROCESSTYPE.INITIATED.getValue());
		return fileUploadParams;
	}
}
