package com.cintap.transport.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.model.DashboardReqParam;
import com.cintap.transport.model.DashboardSummary;
import com.cintap.transport.model.TransactionSourceSummary;
import com.cintap.transport.model.TransactoinSummary;
import com.cintap.transport.repository.trans.TransactionLogRepository;
import com.cintap.transport.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService{

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Override
	public DashboardSummary fetchDashboardSummary(DashboardReqParam otcDashboardReqParam) {
		DashboardSummary otcDashboardSummary  = DashboardSummary.builder().build();

		//Convert String into Date
		String date = convertStrIntoDate(otcDashboardReqParam);  
		//Fetch transaction
		Optional<List<TransactoinSummary>> optLstTransactionLog = transactionLogRepository.dashboardSummary(otcDashboardReqParam.getPartnerId(), date);
		if(optLstTransactionLog.isPresent()) {
			List<TransactoinSummary> lstTransactionSummary = optLstTransactionLog.get();
			otcDashboardSummary.setLstTransactoinSummary(lstTransactionSummary);
			log.info("Trn log = "+TransportCommonUtility.convertObjectToJson(lstTransactionSummary));
		}

		//Fetch transaction
		Optional<List<TransactionSourceSummary>> optLstTransactionSourceSummary = transactionLogRepository.dashboardSourceSummary(otcDashboardReqParam.getPartnerId(), date);
		if(optLstTransactionSourceSummary.isPresent()) {
			List<TransactionSourceSummary> lstTransactionSourceSummary = optLstTransactionSourceSummary.get();
			otcDashboardSummary.setLstTransactionSourceSummary(lstTransactionSourceSummary);
			log.info("Trn log = "+TransportCommonUtility.convertObjectToJson(lstTransactionSourceSummary));
		}
		return otcDashboardSummary;
	}

	private String convertStrIntoDate(DashboardReqParam otcDashboardReqParam) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-d");
		LocalDate localDate = LocalDate.parse(otcDashboardReqParam.getFromDate(), dateFormat);
		String month = localDate.getMonthValue()<10 ? "0"+localDate.getMonthValue() : ""+localDate.getMonthValue();
		return localDate.getYear()+"-"+month;
	}

}
