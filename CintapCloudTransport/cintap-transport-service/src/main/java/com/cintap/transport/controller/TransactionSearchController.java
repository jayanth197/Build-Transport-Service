/**
 * 
 */
package com.cintap.transport.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cintap.transport.model.TransactionCriteria;
import com.cintap.transport.model.TransactionSearchResponse;
import com.cintap.transport.service.TransactionSearchService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@RestController
@RequestMapping("v1")
@Slf4j
public class TransactionSearchController {
	@Autowired
	private TransactionSearchService transactionSearchService;

	@PostMapping("/trnsearch")
	public ResponseEntity<TransactionSearchResponse> fetchTransactionLog(
			@Valid @RequestBody TransactionCriteria transactionCriteria) {
		TransactionSearchResponse transactionSearchResponse=null;
		try {
			transactionSearchResponse = transactionSearchService.freightSearchByExampleQuery(transactionCriteria);
		} catch (Exception exception) {
			log.info("getTransactionLog :: Exception :: ", exception);
		}
		log.info("TransactionSearchController | fetchFreightTransactionLog : Response - ");
		return new ResponseEntity<>(transactionSearchResponse, HttpStatus.OK);
	}

}
