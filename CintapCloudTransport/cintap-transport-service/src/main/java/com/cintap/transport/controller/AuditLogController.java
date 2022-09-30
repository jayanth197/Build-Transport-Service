package com.cintap.transport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cintap.transport.entity.common.AuditLog;
import com.cintap.transport.repository.trans.AuditLogRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1")
@Slf4j
public class AuditLogController{

	@Autowired
	AuditLogRepository auditLogRepository;
	
	@GetMapping("/history/{cmpname}/{recid}")
	public ResponseEntity<List<AuditLog>> getAuditLogByBpiLogId(@PathVariable("cmpname") String cmpName,@PathVariable("recid") Integer recordId)
	{
		log.info("AuditLogController | getAuditLogByBpiLogId :  Request : cmpName - "+cmpName+" RecordId - "+recordId);
		List<AuditLog> listAuditLog=new ArrayList<>();
		boolean isNeedToCall=false;
		Optional<List<AuditLog>> lstAuditLog = auditLogRepository.findByBpiLogId(recordId);
		if(lstAuditLog.isPresent()) {
			listAuditLog = lstAuditLog.get();
			if(CollectionUtils.isEmpty(listAuditLog)) {
				isNeedToCall = true;
			}
		}else {
			isNeedToCall = true;
		}

		if(isNeedToCall) {
			Optional<List<AuditLog>> lstAuditLogObj = auditLogRepository.findByComponentNameAndComponentRecId(cmpName, recordId);
			if(lstAuditLogObj.isPresent()) {
				listAuditLog = lstAuditLogObj.get();
			}
		}
		
		log.info("AuditLogController | getAuditLogByBpiLogId : Response - "+listAuditLog.size());
		return new ResponseEntity<>(listAuditLog,HttpStatus.OK);
	}
}
