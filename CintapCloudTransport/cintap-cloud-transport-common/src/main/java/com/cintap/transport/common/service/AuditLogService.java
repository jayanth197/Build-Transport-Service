package com.cintap.transport.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.AuditLog;
import com.cintap.transport.repository.trans.AuditLogRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditLogService {
	
	@Autowired
	private AuditLogRepository auditLogRepository;
	
	public void saveAuditLog(String partnerId, Integer bpiLogId, String cmpName,Integer componentRecId, String actionType,String action) {
		AuditLog auditLog = AuditLog.builder()
				.partnerId(partnerId)
				.bpiLogId(bpiLogId)
				.componentName(cmpName)
				.componentRecId(componentRecId)
				.actionType(actionType)
				.status("1")
	            .action(action)
				.createdDate(TransportCommonUtility.getCurrentDateTime())
				.createdBy(""+partnerId)
				.build();
		auditLogRepository.save(auditLog);
	}
	
	
	
	
}