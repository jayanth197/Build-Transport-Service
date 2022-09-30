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

import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.entity.trans.BpiLogHeader;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;
import com.cintap.transport.repository.trans.BpiLogDetailRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/")
@Slf4j
public class BpiLogController{
	
	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository; 
	
	@Autowired
	BpiLogDetailRepository bpiLogDetailRepository;
	
	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;
	
	@GetMapping(value="/log/{partnerId}")
	public ResponseEntity<List<BpiLogHeader>> getBpiLogHeader(@PathVariable("partnerId") Integer partnerId)
	{
		log.info("BpiLogController | getBpiLogHeader : Request : partnerId - "+partnerId);
		List<BpiLogHeader> lstBpiLogHeader=new ArrayList<>();
		try {
			Optional<List<BpiLogHeader>> optBpiLogHeader = bpiLogHeaderRepository.findBySenderPartnerIdOrderByLogHdrIdDesc(partnerId);
			if(optBpiLogHeader.isPresent()) {
				lstBpiLogHeader=optBpiLogHeader.get();
			}else {
				lstBpiLogHeader=new ArrayList<>();
			}
		}catch (Exception e) {
			log.info("Transaction Type ERROR ::",e);
		}
		log.info("BpiLogController | getBpiLogHeader : Response - "+lstBpiLogHeader.size());
		return new ResponseEntity<>(lstBpiLogHeader,HttpStatus.OK);
	}
	
	@GetMapping(value="/logdetail/{loghdrid}")
	public ResponseEntity<List<BpiLogDetail>> getBpiLogDetail(@PathVariable("loghdrid") Integer logHdrId)
	{
		log.info("BpiLogController | getBpiLogDetail : Request : loghdrid - "+logHdrId);
		List<BpiLogDetail> lstBpiLogDetail=new ArrayList<>();
		try {
			Optional<List<BpiLogDetail>> optBpiLogDetail = bpiLogDetailRepository.findByLogHeaderId(logHdrId);
			if(optBpiLogDetail.isPresent()) {
				lstBpiLogDetail=optBpiLogDetail.get();
			}else {
				lstBpiLogDetail=new ArrayList<>();
			}
		}catch (Exception e) {
			log.info("Transaction Type ERROR ::",e);
		}
		log.info("BpiLogController | getBpiLogDetail : Response - "+lstBpiLogDetail.size());
		return new ResponseEntity<>(lstBpiLogDetail,HttpStatus.OK);
	}
	
	@GetMapping(value="/loghdr/{bpilogid}")
	public ResponseEntity<List<BpiLogHeader>> getBpiHeaderByBpiLogId(@PathVariable("bpilogid") Integer bpiLogId)
	{
		log.info("BpiLogController | getBpiHeaderByBpiLogId : Request : loghdrid - "+bpiLogId);
		List<BpiLogHeader> bpiLogHeader = null;
		try {
			Optional<List<BpiLogHeader>> optLstBpiLogHeader = bpiLogHeaderRepository.findByBpiLogId(bpiLogId);
			if(optLstBpiLogHeader.isPresent()) {
				bpiLogHeader = optLstBpiLogHeader.get();
			}
		}catch (Exception e) {
			log.info("Transaction Type ERROR ::",e);
		}
		log.info("BpiLogController | getBpiLogDetail : Response sent ");
		return new ResponseEntity<>(bpiLogHeader,HttpStatus.OK);
	}

	@GetMapping(value="/loghdr/{bpilogid}/{direction}")
	public ResponseEntity<BpiLogHeader> getBpiHeaderByBpiLogIdAndDirection(@PathVariable("bpilogid") Integer bpiLogId,@PathVariable("direction") String direction)
	{
		log.info("BpiLogController | getBpiHeaderByBpiLogId : Request : loghdrid - "+bpiLogId+" And Direction : "+direction);
		BpiLogHeader bpiLogHeader = null;
		try {
			Optional<BpiLogHeader> optBpiLogHeader = bpiLogHeaderRepository.findByBpiLogIdAndDirection(bpiLogId,direction);
			if(optBpiLogHeader.isPresent()) {
				bpiLogHeader = optBpiLogHeader.get();
			}
		}catch (Exception e) {
			log.info("Transaction Type ERROR ::",e);
		}
		log.info("BpiLogController | getBpiLogDetail : Response sent ");
		return new ResponseEntity<>(bpiLogHeader,HttpStatus.OK);
	}
	
	@GetMapping(value="/rawFile/{bpilogid}/{transactionType}")
	public ResponseEntity<List<TransactionLogInboundOutbound>> getRawFile(@PathVariable("bpilogid") Integer bpiLogId, @PathVariable("transactionType") String transType)
	{
		log.info("BpiLogController | getBpiHeaderByBpiLogId : Request : loghdrid - "+bpiLogId);
		List<TransactionLogInboundOutbound> transactionLogInboundOutbound = null;
		try {
			Optional<List<TransactionLogInboundOutbound>> optTransactionLogInboundOutbound = transactionLogInboundOutboundRepository.findByBpiLogIdAndTransactionType(bpiLogId, transType);
			if(optTransactionLogInboundOutbound.isPresent()) {
				transactionLogInboundOutbound = optTransactionLogInboundOutbound.get();
			}
		}catch (Exception e) {
			log.info("Transaction Type ERROR ::",e);
		}
		log.info("BpiLogController | getBpiLogDetail : Response sent ");
		return new ResponseEntity<>(transactionLogInboundOutbound,HttpStatus.OK);
	}
}
