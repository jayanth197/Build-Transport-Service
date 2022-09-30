package com.cintap.transport.aperak.outbound;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import com.cintap.transport.aperak.xml.parser.AperakOutboundEdifactParser;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.CONNECTIONTYPE;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.ftp.service.FtpConnectionService;
import com.cintap.transport.common.ftp.service.FtpFileOperationsService;
import com.cintap.transport.common.message.model.Connection;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.sftp.service.SFtpFileOperationsService;
import com.cintap.transport.common.sftp.service.SftpConnectionService;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.common.util.TransportConstants;
import com.cintap.transport.model.TransactionAperakInfo;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StandardOutboundAperakService {

	@Autowired
	AperakOutboundEdifactParser aperakOutboundEdifactParser;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	private FtpConnectionService ftpConnectionService;

	@Autowired
	private FtpFileOperationsService ftpFileOperationsService;

	@Autowired
	private SFtpFileOperationsService sFtpFileOperationsService;

	@Autowired
	SftpConnectionService sftpConnectionService;

	public void processOutboundRequest(Connection connectionObj, FileUploadParams fileUploadParams) {

		log.info("StandardOutboundAperakService : Request received");
		try {
			Optional<List<TransactionAperakInfo>> transactionAperakInfoOpt = transactionLogRepository
					.getTransactionLogForAperak(fileUploadParams.getReceiverPartnerId(),
							fileUploadParams.getSenderPartnerId());
			if (transactionAperakInfoOpt.isPresent()) {

				List<TransactionAperakInfo> lstTransactionAperakInfo = transactionAperakInfoOpt.get();

				for (TransactionAperakInfo transactionAperakInfo : lstTransactionAperakInfo) {

					String fileName = connectionObj.getFileName() + "_"
							+ transactionAperakInfo.getPartnerTransactionId() + "_"
							+ transactionAperakInfo.getBpiLogId() + "_" + getFileDateFormatInCS() + "_"
							+ transactionAperakInfo.getAckType() + "." + fileUploadParams.getFileType();

					String targetFilepath = connectionObj.getTargetDirectory() + "" + fileName;

					String convertedFileData = transactionAperakInfo.getSentRawFile();

					if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
						ftpFileOperationsService.moveFileToTarget(connectionObj, connectionObj.getFileName(),
								convertedFileData, targetFilepath);
					} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
						sFtpFileOperationsService.moveFileToTarget(connectionObj, connectionObj.getFileName(),
								convertedFileData, targetFilepath);
					}
					transactionLogInboundOutboundRepository.updateIsSent(transactionAperakInfo.getInOutId());

					Integer ackStatus = TransportCommonUtility.getSentAckStatus(transactionAperakInfo.getAckType());

					transactionLogRepository.updateAckStatus(transactionAperakInfo.getBpiLogId(), ackStatus);
				}
			}
		} catch (Exception e) {
			log.info("Exception is : " + e);
			String msg = e.getMessage();
			log.info(fileUploadParams.getBatchId() + " :: Failed processing EDI file :: " + msg);
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());

			if (e instanceof DataIntegrityViolationException) {
				msg = "Duplicate Transaction";
				/**
				 * Reject transaction in the case of Duplicate Order
				 */

			}

			fileUploadParams.setTransactionErrorReason(msg);
			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(), msg,
					0);
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());
			transportCommonService.updateBpiHeader(fileUploadParams);
		}
	}

	public static String getFileDateFormatInCS() {
		SimpleDateFormat format = new SimpleDateFormat(TransportConstants.DATE_DDMMYYYYHHMMSS);
		format.setTimeZone(TimeZone.getTimeZone("CST"));
		return format.format(new Date());
	}

}
