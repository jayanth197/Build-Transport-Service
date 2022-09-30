package com.cintap.transport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.trans.TransactionTrail;
import com.cintap.transport.model.ApiResponse;
import com.cintap.transport.repository.TransactionTrailRepository;
import com.cintap.transport.service.TransactionTrailService;

@Service
public class TransactionTrailServiceImpl implements TransactionTrailService{

	@Autowired
	TransactionTrailRepository transaTrailRepository;

	@Override
	public ApiResponse saveTransactionTrail(TransactionTrail transactionTrail) {
		transactionTrail.setCreatedDate(TransportCommonUtility.getCurrentDateTime());
		transaTrailRepository.save(transactionTrail);
		return ApiResponse.builder().statusCode("000").statusMessage("Successfully saved").build();
	}

	@Override
	public List<TransactionTrail> fetchTransactionTrail() {
		return null;
	}
	

}
