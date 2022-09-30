package com.cintap.transport.common.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.entity.trans.TransactionTrail;
import com.cintap.transport.repository.TransactionTrailRepository;

@Service
public class TransactionTrailService {

	
	@Autowired
	TransactionTrailRepository transactionTrailRepository;
	
	public TransactionTrail buildTransactionTrail(TransactionLog transactionLog, String parentTransactionId) {
		// Logic - pull master trans trail record
		Optional<TransactionTrail> optTransTrail = transactionTrailRepository.findByTrail(parentTransactionId, transactionLog.getStpTransId(), transactionLog.getRtpId());
		TransactionTrail masterTrnTrail = null;
		//call repository method to check if there is a master record
		if(optTransTrail.isPresent()) {
			masterTrnTrail = optTransTrail.get();					
		}
		// Create Trans trail record
		TransactionTrail transactionTrail = TransactionTrail.builder()
				.bpiLogId(transactionLog.getBpiLogId())
				.masterBpiLogId(transactionLog.getBpiLogId())
				.senderPartner(transactionLog.getStpId())
				.receiverPartner(transactionLog.getRtpId())
				.trailTransId(transactionLog.getStpTransId())
				.trailTransType(transactionLog.getStpTypeId())
				.createdBy(transactionLog.getStpId())
				.createdDate(TransportCommonUtility.getCurrentDateTime())
				.isMaster(1)
				.build();

		if(masterTrnTrail!=null) {
			transactionTrail.setMasterBpiLogId(masterTrnTrail.getBpiLogId());
			transactionTrail.setMasterTransId(masterTrnTrail.getMasterTransId());
			transactionTrail.setMasterTransType(masterTrnTrail.getMasterTransType());
			transactionTrail.setIsMaster(0);
		}
		transactionTrailRepository.save(transactionTrail);
		return transactionTrail;
	}
	
}
