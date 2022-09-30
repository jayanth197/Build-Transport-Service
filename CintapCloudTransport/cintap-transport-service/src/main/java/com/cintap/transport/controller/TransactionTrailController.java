 package com.cintap.transport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cintap.transport.common.service.TransactionTrailService;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.trans.TransactionTrail;
import com.cintap.transport.repository.TransactionTrailRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1")
@Slf4j
public class TransactionTrailController {
	
	@Autowired
	TransactionTrailRepository transactionTrailRepository; 
	
	@Autowired
	TransactionTrailService transactionTrailService;

	@Autowired
	TransportCommonUtility transportCommonUtility;

	@GetMapping("/trail/{bpiLogId}")
	public ResponseEntity<List<TransactionTrail>> getAllByPartnerId(@PathVariable("bpiLogId") final Integer bpiLogId)
	{
		log.info("TransactionTrailController | getAllByPartnerId -  Request : bpiLogId - "+bpiLogId);
		List<TransactionTrail> lstTransTrail=new ArrayList<>();
		try
		{
			Optional<TransactionTrail> optTransTrail = transactionTrailRepository.findByBpiLogId(bpiLogId);
			if(optTransTrail.isPresent()) {
				Optional<List<TransactionTrail>> optMasterTransTrail = transactionTrailRepository.findByMasterBpiLogId(optTransTrail.get().getMasterBpiLogId());
				if(optMasterTransTrail.isPresent()) {
					lstTransTrail = optMasterTransTrail.get();
				}
			}
			
		}catch (Exception exception) {
			log.info("TransactionTrailController :: getAllByPartnerId - ",exception);
		}
		log.info("TransactionTrailController | getAllByPartnerId : Response - "+lstTransTrail.size());
		return new ResponseEntity<>(lstTransTrail,HttpStatus.OK);
	}
}
