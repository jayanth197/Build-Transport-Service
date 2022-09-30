/**
 * 
 */
package com.cintap.transport.service;

import java.util.List;

import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.model.TransactionCriteria;
import com.cintap.transport.model.TransactionSearchResponse;

/**
 * @author SurenderMogiloju
 *
 */
public interface TransactionSearchService {
	List<TransactionLog> trnSearchByExampleQuery(TransactionCriteria trnCriteria);
	TransactionSearchResponse freightSearchByExampleQuery(TransactionCriteria trnCriteria);
}
