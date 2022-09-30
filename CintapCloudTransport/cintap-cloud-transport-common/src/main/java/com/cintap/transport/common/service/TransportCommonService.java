package com.cintap.transport.common.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.EDIFACTTRANSACTIONTYPE;
import com.cintap.transport.common.enums.EDIPROCESSTYPE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.BatchSequenceId;
import com.cintap.transport.entity.common.GroupSequenceId;
import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.entity.trans.BpiLogHeader;
import com.cintap.transport.repository.common.BatchSeqIdRepository;
import com.cintap.transport.repository.common.GroupSeqIdRepository;
import com.cintap.transport.repository.trans.BpiLogDetailRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransportCommonService {

	@Autowired
	BpiLogDetailRepository bpiLogDetailRepository;
	
	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;
	
	@Autowired
	GroupSeqIdRepository groupSeqIdRepository;
	
	@Autowired
	BatchSeqIdRepository batchSeqIdRepository;
	
	@Autowired
	CommonServiceImpl commonServiceImpl;
	
	public FileUploadParams bpiLogging(FileUploadParams fileUploadParams) {
		BpiLogHeader bpiLogHeader = null;
		if (fileUploadParams.getBpiHeaderLogId() == null) {
			bpiLogHeader = saveBpiHeader(fileUploadParams);
		}
		if (bpiLogHeader != null) {
			fileUploadParams.setBpiHeaderLogId(bpiLogHeader.getLogHdrId());
		}
		return fileUploadParams;
	}

	public void saveBpiLogDetail(Integer logHdrId, String logType, String logDetails, Integer processId) {
		BpiLogDetail logDetail = BpiLogDetail.builder().logHeaderId(logHdrId).processId(processId).logType(logType)
				.logDetails(logDetails).createdDate(TransportCommonUtility.getCurrentDateTime()).build();
		bpiLogDetailRepository.save(logDetail);
	}
	
	public BpiLogDetail getBpiLogDetail(Integer logHdrId, String logType, String logDetails, Integer processId) {
		BpiLogDetail logDetail = BpiLogDetail.builder().logHeaderId(logHdrId).processId(processId).logType(logType)
				.logDetails(logDetails).createdDate(TransportCommonUtility.getCurrentDateTime()).build();
		return logDetail;
	}
	
	public void saveBpiLogDetailAperak(FileUploadParams fileUploadParams) {
		for (BpiLogDetail bpiLogDetail : fileUploadParams.getLstLogDetails()) {
			bpiLogDetail.setLogDetailId(null);
			log.info("bpiLogDetail saving  is : " + bpiLogDetail);
			bpiLogDetail.setLogHeaderId(fileUploadParams.getBpiHeaderLogId());
			bpiLogDetailRepository.save(bpiLogDetail);
			log.info("bpiLogDetail saved is : " + bpiLogDetail);
		}
	}
	
	public FileUploadParams dumpLogDetails(FileUploadParams fileUploadParams) {
		BpiLogHeader bpiHeaderLog = null;
		if (!EDIFACTTRANSACTIONTYPE.APERAK.getValue().equalsIgnoreCase(fileUploadParams.getTrnType())) {
			bpiHeaderLog = commonServiceImpl.saveBpiHeader(fileUploadParams);

			fileUploadParams.setBpiHeaderLogId(bpiHeaderLog.getLogHdrId());
			fileUploadParams.setGroupId(bpiHeaderLog.getGroupId());
			for (BpiLogDetail bpiLogDetail : fileUploadParams.getLstLogDetails()) {
				bpiLogDetail.setLogDetailId(null);
				log.info("bpiLogDetail saving  is : " + bpiLogDetail);
				bpiLogDetail.setLogHeaderId(bpiHeaderLog.getLogHdrId());
				bpiLogDetailRepository.save(bpiLogDetail);
				log.info("bpiLogDetail saved is : " + bpiLogDetail);
			}
		}
		return fileUploadParams;
	}

	public BpiLogHeader updateBpiHeader(FileUploadParams fileUploadParams) {
		Optional<BpiLogHeader> optBpiLogHeader;
		BpiLogHeader logHeader = null;
		if (fileUploadParams.getBpiHeaderLogId() != null) {
			optBpiLogHeader = bpiLogHeaderRepository.findById(fileUploadParams.getBpiHeaderLogId());
			if (optBpiLogHeader.isPresent()) {
				logHeader = optBpiLogHeader.get();
				logHeader.setRawFile(fileUploadParams.getRawFile());
				logHeader.setSenderPtnrDispName(fileUploadParams.getSenderPartnerDisplayName());
				logHeader.setReceiverPtnrDispName(fileUploadParams.getReceiverPartnerDisplayName());
				logHeader.setBatchStatus(fileUploadParams.getBatchStatus());
				logHeader.setNoOfTransactions(fileUploadParams.getTxnCount());
				logHeader.setUpdatedDate(TransportCommonUtility.getCurrentDateTime());
				logHeader.setEndDate(TransportCommonUtility.getCurrentDateTime());
				logHeader.setBpiLogId(fileUploadParams.getBpiLogId() != null ? fileUploadParams.getBpiLogId() : 0);
				logHeader = bpiLogHeaderRepository.save(logHeader);
			}
		}
		return logHeader;
	}

	public BpiLogHeader saveBpiHeader(FileUploadParams fileUploadParams) {
		BpiLogHeader pfLogHeader = BpiLogHeader.builder()
				.groupId(null != fileUploadParams.getGroupId() ? fileUploadParams.getGroupId()
						: generateGroupId(fileUploadParams.getGroupId(), fileUploadParams.getFileName()))
				.batchId(generateBatchId(fileUploadParams.getBatchId(), fileUploadParams.getFileName()))
				.processFlowId(fileUploadParams.getProcessFlowId()).direction(fileUploadParams.getDirection())
				.type(fileUploadParams.getFileType())
				.senderPartnerId(fileUploadParams.getSenderPartnerId() != null
				? Integer.parseInt(fileUploadParams.getSenderPartnerId())
						: 0)
				.receiverPartnerId(fileUploadParams.getReceiverPartnerId() != null
				? Integer.parseInt(fileUploadParams.getReceiverPartnerId())
						: 0)
				.senderPtnrDispName(fileUploadParams.getSenderPartnerDisplayName() != null
				? fileUploadParams.getSenderPartnerDisplayName()
						: "")
				.receiverPtnrDispName(fileUploadParams.getReceiverPartnerDisplayName() != null
				? fileUploadParams.getReceiverPartnerDisplayName()
						: "")
				.fileCount(fileUploadParams.getFileCount()).fileName(fileUploadParams.getFileName())
				.batchStatus(EDIPROCESSTYPE.INITIATED.getValue()).noOfTransactions(0)
				.rawFile(fileUploadParams.getRawFile())
				.direction(DIRECTION.INBOUND.getDirection())
				.createdBy(fileUploadParams.getSenderPartnerId() != null ? fileUploadParams.getSenderPartnerId() : "")
				.createdDate(TransportCommonUtility.getCurrentDateTime()).build();
		pfLogHeader = bpiLogHeaderRepository.save(pfLogHeader);
		log.info("BpiLogHeader Saved : " + pfLogHeader);
		return pfLogHeader;

	}
	
	/**
	 * Generating GroupID from database Sequence
	 * 
	 * @param groupId
	 * @param fileName
	 * @return
	 */
	private Integer generateGroupId(Integer groupId, String fileName) {
		if (groupId == null) {
			GroupSequenceId groupSeqId = groupSeqIdRepository
					.save(GroupSequenceId.builder().fileName(fileName).build());
			groupId = groupSeqId.getGroupId();
		}
		return groupId;
	}

	/**
	 * Generating BatchId from database sequence
	 * 
	 * @param batchId
	 * @param fileName
	 * @return
	 */
	private Integer generateBatchId(Integer batchId, String fileName) {
		if (batchId == null) {
			BatchSequenceId batchSeqId = batchSeqIdRepository
					.save(BatchSequenceId.builder().fileName(fileName).build());
			batchId = batchSeqId.getBatchId();
		}
		return batchId;
	}
}
