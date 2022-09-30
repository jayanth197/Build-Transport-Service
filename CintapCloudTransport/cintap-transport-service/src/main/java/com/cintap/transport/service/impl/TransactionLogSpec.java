/**
 * 
 */
package com.cintap.transport.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.model.TransactionCriteria;

/**
 * @author SurenderMogiloju
 *
 */
public class TransactionLogSpec implements Specification<TransactionLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TransactionCriteria transactionCriteria;

	public TransactionLogSpec(TransactionCriteria searchCriteria) {
		this.transactionCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<TransactionLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> pred = new ArrayList<>();

		if (!StringUtils.isEmpty(transactionCriteria.getTransactionNumber())) {
			predicates.add(cb.like(root.get("stpTransId"), "%"+transactionCriteria.getTransactionNumber()+"%"));
		}

		if (!StringUtils.isEmpty(transactionCriteria.getTransactionType())) {
			predicates.add(cb.equal(root.get("stpSourceId"), transactionCriteria.getTransactionType()));
		}

		if (!StringUtils.isEmpty(transactionCriteria.getFileType())) {
			predicates.add(cb.equal(root.get("fileType"), transactionCriteria.getFileType()));
		}
		
		if (!StringUtils.isEmpty(transactionCriteria.getProcessType())) {
			predicates.add(cb.equal(root.get("processType"), transactionCriteria.getProcessType()));
		}
		
		if (!StringUtils.isEmpty(transactionCriteria.getSenderPartner())) {
			predicates.add(cb.equal(root.get("stpId"), transactionCriteria.getSenderPartner()));
		}
		
		if (!StringUtils.isEmpty(transactionCriteria.getReceiverPartner())) {
			predicates.add(cb.equal(root.get("rtpId"), transactionCriteria.getReceiverPartner()));
		}
		
		if (StringUtils.isEmpty(transactionCriteria.getSenderPartner())
				&& StringUtils.isEmpty(transactionCriteria.getReceiverPartner())) {
			String partnerId = transactionCriteria.getPartnerId();
			pred.add(cb.equal(root.get("stpId"), partnerId));
			pred.add(cb.equal(root.get("rtpId"), partnerId));
			predicates.add(orTogether(pred, cb));
		}

		if (transactionCriteria.getStatus() != null && transactionCriteria.getStatus() > 0) {
			predicates.add(cb.equal(root.get("statusId"), transactionCriteria.getStatus()));
		}
		
		if (transactionCriteria.getBpiLogId() != null && transactionCriteria.getBpiLogId() > 0) {
			predicates.add(cb.equal(root.get("bpiLogId"), transactionCriteria.getBpiLogId()));
		}
		
		if (!StringUtils.isEmpty(transactionCriteria.getFromDate())
				&& !StringUtils.isEmpty(transactionCriteria.getToDate())) {
			predicates.add(
					cb.between(root.get("createdDate").as(Date.class), convertStringToDate(transactionCriteria.getFromDate()),
							convertStringToDate(transactionCriteria.getToDate())));
		}
		 
		if (!StringUtils.isEmpty(transactionCriteria.getCreatedDate())) {
			predicates.add(cb.like(root.get("createdDate"),  "%" +transactionCriteria.getCreatedDate()+ "%" ));
		}
		
		//Filtering Cancelled transactions from list
		predicates.add(cb.notEqual(root.get("statusId"), 15));
		
		query.orderBy(cb.desc(root.get("bpiLogId")));
		return andTogether(predicates, cb);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}
	
	private Predicate orTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.or(predicates.toArray(new Predicate[0]));
	}
	
	private Date convertStringToDate(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
