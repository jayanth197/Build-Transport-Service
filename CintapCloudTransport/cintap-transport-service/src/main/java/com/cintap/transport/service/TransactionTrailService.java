package com.cintap.transport.service;

import java.util.List;

import com.cintap.transport.entity.trans.TransactionTrail;
import com.cintap.transport.model.ApiResponse;

public interface TransactionTrailService {
	ApiResponse saveTransactionTrail(TransactionTrail transactionTrail);
	List<TransactionTrail> fetchTransactionTrail();
}

