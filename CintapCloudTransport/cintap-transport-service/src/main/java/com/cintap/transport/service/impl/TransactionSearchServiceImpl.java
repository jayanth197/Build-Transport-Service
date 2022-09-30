package com.cintap.transport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.model.Pageable;
import com.cintap.transport.model.TransactionCriteria;
import com.cintap.transport.model.TransactionSearchResponse;
import com.cintap.transport.repository.trans.TransactionLogRepository;
import com.cintap.transport.service.TransactionSearchService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionSearchServiceImpl implements TransactionSearchService{

	@Autowired
	private TransactionLogRepository trnLogRepository;

	@Override
	public List<TransactionLog> trnSearchByExampleQuery(TransactionCriteria trnCriteria) {

		log.info("TransactionSearchServiceImpl - trnSearchByExampleQuery");

		List<TransactionLog> listTransactionLog = new ArrayList<>();

		switch(trnCriteria.getFilterType()) {
		case "DEFAULT": {
			log.info("TransactionSearchServiceImpl - trnSearchByExampleQuery : PartnerID Default Serch");
			Optional<List<TransactionLog>> lstTransLog = trnLogRepository.findByPartnerId(trnCriteria.getPartnerId());
			if(lstTransLog.isPresent()) {
				listTransactionLog = lstTransLog.get();
			}
			return listTransactionLog;
		}

		case "TRNFILTER":{
			// Transaction Filter
			TransactionLogSpec spec = new TransactionLogSpec(trnCriteria);
			listTransactionLog = trnLogRepository.findAll(spec);
			return listTransactionLog;
		}
		default:{  }
		}
		return new ArrayList<>();

	}

	@Override
	public TransactionSearchResponse freightSearchByExampleQuery(TransactionCriteria trnCriteria) {

		log.info("TransactionSearchServiceImpl - trnSearchByExampleQuery");
		List<TransactionLog> lstTrnLogDtls = new ArrayList<>();
		int totalPages=0;
		long totalRecords = 0;
		List<TransactionLog> listTransactionLog = new ArrayList<>();

		org.springframework.data.domain.Pageable paging = PageRequest.of(trnCriteria.getPageNo(),trnCriteria.getPageSize(), Sort.by(Direction.DESC, trnCriteria.getSortBy()));

		switch(trnCriteria.getFilterType()) {

		case "DEFAULT": {
			log.info("TransactionSearchServiceImpl - trnSearchByExampleQuery : PartnerID Default Serch");

			Page<TransactionLog> pageTrnLog = null;
			List<TransactionLog> list = null;	
			if("OTC".equalsIgnoreCase(trnCriteria.getModuleType())) {
				list = trnLogRepository.findByPartnerIdWithPageOtc(trnCriteria.getPartnerId());
				pageTrnLog = trnLogRepository.findByPartnerIdWithPageOtcWithPagination(trnCriteria.getPartnerId(),paging);
			}else {
				list = trnLogRepository.findByPartnerIdWithPage(trnCriteria.getPartnerId());
				pageTrnLog = trnLogRepository.findByPartnerIdWithPageWithPagination(trnCriteria.getPartnerId(),paging);
			}

			if(!CollectionUtils.isEmpty(list)) {
				totalRecords = list.size();
			}
			if(pageTrnLog.hasContent()) {
				totalPages = pageTrnLog.getTotalPages();
				listTransactionLog = pageTrnLog.getContent();
				buildTrnAdditonalInfo(lstTrnLogDtls, listTransactionLog);
			}
			break;
		}
		case "TRNFILTER":{
			// Transaction Filter
			org.springframework.data.domain.Pageable paging1 = PageRequest.of(trnCriteria.getPageNo(),trnCriteria.getPageSize(), Sort.by(Direction.DESC, trnCriteria.getSortBy()));
			TransactionLogSpec spec = new TransactionLogSpec(trnCriteria);
			Page<TransactionLog> pageTrnLog = null;
			List<TransactionLog> list = trnLogRepository.findAll(spec);
			if(!CollectionUtils.isEmpty(list)) {
				totalRecords = list.size();
			}
			pageTrnLog = trnLogRepository.findAll(spec,paging1);
			if(pageTrnLog.hasContent()) {
				totalPages = pageTrnLog.getTotalPages();
				listTransactionLog = pageTrnLog.getContent();
				buildTrnAdditonalInfo(lstTrnLogDtls, listTransactionLog);
			}
			
			break;
		}
		}
		return buildInvoiceSerachResponse(trnCriteria.getPageNo(),trnCriteria.getPageSize(),totalPages,listTransactionLog,totalRecords);

	}

	private void buildTrnAdditonalInfo(List<TransactionLog> lstTrnLogDtls, List<TransactionLog> listTransactionLog) {
		for(TransactionLog trnObj:listTransactionLog) {
			lstTrnLogDtls.add(trnObj);
		}
	}

	private TransactionSearchResponse buildInvoiceSerachResponse(Integer pageNo,Integer pageSize,Integer totalPage,List<TransactionLog> listTransactionLog,long totalRecords) {
		return TransactionSearchResponse.builder()
				.pegable(buildPage(pageNo,pageSize,totalPage))
				.transactions(listTransactionLog)
				.totalRecords(totalRecords)
				.build();
	}

	/**
	 * 
	 *  Building static pagination
	 */
	private Pageable buildPage(Integer pageNo,Integer pageSize,Integer totalPage) {
		return Pageable.builder()
				.pageNo(pageNo)
				.pageSize(pageSize)
				.totalPages(totalPage)
				.build();
	}

}
