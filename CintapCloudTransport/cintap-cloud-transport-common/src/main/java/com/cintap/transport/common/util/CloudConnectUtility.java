package com.cintap.transport.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.ErrorLog;
import com.cintap.transport.repository.common.BpiErrorLogRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CloudConnectUtility {

	@Autowired
	private BpiErrorLogRepository bpiErrorLogRepository;

	public void saveErrorLog(Exception exception) {
		StackTraceElement[] stackTrace = exception.getStackTrace();
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		ErrorLog bpiErrorLog = ErrorLog.builder()
				.className(stackTrace[0].getClassName())
				.funName(stackTrace[0].getMethodName())
				.fileName(stackTrace[0].getFileName())
				.errorDescription(sw.toString())
				.exception(exception.toString())
				.createdDate(TransportCommonUtility.getCurrentDateTime())
				.createdBy("System")
				.build();
		log.info("Exception : {}",sw.toString());
		bpiErrorLogRepository.save(bpiErrorLog);
	}
}
