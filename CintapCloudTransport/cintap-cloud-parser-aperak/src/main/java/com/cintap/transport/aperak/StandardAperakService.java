package com.cintap.transport.aperak;

import com.cintap.transport.aperak.outbound.StandardOutboundAperakService;
import com.cintap.transport.aperak.xml.parser.AperakXmlToEdifactParser;
import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.FILETYPE;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StandardAperakService {

	@Autowired
	AperakXmlToEdifactParser aperakInboundEdifactParser;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	@Autowired
	TransportCommonService transportCommonService;
	
	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;
	
	@Autowired
	StandardOutboundAperakService standardOutboundAperakEdifactService;
	
	@Autowired
	TransactionLogRepository transactionLogRepository;

	
	public FileUploadParams processAperakRequest(String fileData, FileUploadParams fileUploadParams) {

		log.info("StandardAperakService : Request received");

		// Map data from interchange to Database entity
		if (FILETYPE.XML.getValue().equalsIgnoreCase(fileUploadParams.getFileType())) {
			fileUploadParams = aperakInboundEdifactParser.convertModelToEntity(fileData, fileUploadParams);
		}

		if (null == fileUploadParams.getBpiLogId()) {
			log.info("StandardAperakService : Unable to convert into Entity");

			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
					TRANSPORTLOG.MAPPING_FAILED.getValue(), 0);
			return fileUploadParams;
		}
		Integer bpiLogId = fileUploadParams.getBpiLogId();
		
		TransactionLogInboundOutbound transactionLogInboundOutbound = TransactionLogInboundOutbound.builder()
				.bpiLogId(bpiLogId)
				.fileName(fileUploadParams.getFileName())
				.fileType(fileUploadParams.getFileType())
				.receivedRawFile(fileData)
				.sentRawFile(fileUploadParams.getOutboundRawFile())
				.transactionType(fileUploadParams.getTrnType())
				.build();

		transactionLogInboundOutboundRepository.save(transactionLogInboundOutbound);

		log.info("StandardAperakService : Transaction Log Inbound Outbound "
				+ transactionLogInboundOutbound);
		
		bpiLogHeaderRepository.updateBpiLogId(bpiLogId, fileUploadParams.getBpiHeaderLogId());

		auditLogService.saveAuditLog(fileUploadParams.getSenderPartnerId(), 
				bpiLogId,
				COMPONENTNAME.TRANSACTION.getComponentName(), 
				bpiLogId, 
				ACTIONTYPE.CREATE.getAction(), 
				"APERAK parsed successfully # " + bpiLogId);

		transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
				TRANSPORTLOG.TRANSACTION_CREATED.getValue() + " transactionLogInboundOutbound: " + transactionLogInboundOutbound.getId(), 0);
		
		return fileUploadParams;
	}

}
