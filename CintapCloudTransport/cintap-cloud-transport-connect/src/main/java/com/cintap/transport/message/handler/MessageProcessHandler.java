/**
 * 
 */
package com.cintap.transport.message.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.TimeZone;
import java.util.Vector;

import com.cintap.transport.aperak.outbound.StandardOutboundAperakService;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.CONNECTIONTYPE;
import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.EDIFACTTRANSACTIONTYPE;
import com.cintap.transport.common.enums.FILETYPE;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.ftp.service.FtpConnectionService;
import com.cintap.transport.common.ftp.service.FtpFileOperationsService;
import com.cintap.transport.common.message.model.Connection;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.CommonServiceImpl;
import com.cintap.transport.common.service.SerialNumberService;
import com.cintap.transport.common.service.XmlFileFormatCovert;
import com.cintap.transport.common.sftp.service.SFtpFileOperationsService;
import com.cintap.transport.common.sftp.service.SftpConnectionService;
import com.cintap.transport.common.util.TransportCommonBuilder;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.edi.qm.inbound.StandardInboundShipmentTrackingEdiService;
import com.cintap.transport.ediparser.dto.global.Response;
import com.cintap.transport.entity.common.ProcessExecutionLog;
import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.entity.trans.BpiLogHeader;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.exception.InvalidEdiFileException;
import com.cintap.transport.jms.KafkaSender;
import com.cintap.transport.message.model.ConnectionMapDetail;
import com.cintap.transport.message.model.Maps;
import com.cintap.transport.message.model.ProcessStatus;
import com.cintap.transport.parsing.service.StandardInboundFileProcessService;
import com.cintap.transport.parsing.service.StandardOutboundFileProcessService;
import com.cintap.transport.repository.common.ProcessExecutionLogRepository;
import com.cintap.transport.repository.trans.BpiLogDetailRepository;
import com.cintap.transport.repository.trans.TransactionLogInboundOutboundRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;
import com.cintap.transport.util.CintapBpiConstants;
import com.cintap.transport.util.CintapMDC;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class MessageProcessHandler {

	@Autowired
	private FtpConnectionService ftpConnectionService;

	@Autowired
	private BpiLogDetailRepository bpiLogDetailRepository;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private FtpFileOperationsService ftpFileOperationsService;

	@Autowired
	private SFtpFileOperationsService sFtpFileOperationsService;

	@Autowired
	private CommonServiceImpl commonServiceImpl;

	@Autowired
	SftpConnectionService sftpConnectionService;
	
	@Autowired
	SerialNumberService serialNumberService;

	@Autowired
	private ProcessExecutionLogRepository processExecutionLogRepository;

	@Autowired
	private XmlFileFormatCovert xmlFileFormatCovert;

	@Autowired
	private KafkaSender kafkaSender;

	@Autowired
	private StandardOutboundFileProcessService standardOutboundFileProcessService;

	@Autowired
	private StandardInboundFileProcessService standardInboundFileProcessService;

	@Autowired
	private StandardOutboundAperakService standardOutboundAperakService;

	@Autowired
	TransportCommonBuilder transportCommonBuilder;

	@Autowired
	TransactionLogInboundOutboundRepository transactionLogInboundOutboundRepository;

	@Value("${ack.file.name.extension}")
	private String ackFileNameExtension;

	@Value("${process.status.queue.to.bpi}")
	private String processStatusQueue;

	@Autowired
	private TransportCommonUtility transportCommonUtility;

	@Autowired
	StandardInboundShipmentTrackingEdiService standardInboundShipmentTrackingEdiService;

	@Async("messageProcessHandlerExecutor")
	public void processMessage(String message) {
		ProcessExecutionLog processExecutionLog = null;
		ConnectionMapDetail connectionMapDetail = null;
		try {
			log.info("1. Message received is in processMessage method ###");
			boolean isConnectionSuccess = false;
			FTPClient ftpClient = null;
			ChannelSftp sftpClient = null;
			Connection connectionObj = null;
			Maps maps = null;
			com.cintap.transport.message.model.Process processObj = null;
			List<BpiLogDetail> commonLogList = new ArrayList<>();
			connectionMapDetail = (ConnectionMapDetail) TransportCommonUtility.convertJSONToObject(message,
					ConnectionMapDetail.class);
			log.info("ConnectionMapDetail is : " + connectionMapDetail.toString());
			if (null != connectionMapDetail) {
				connectionObj = connectionMapDetail.getConnection();
				maps = connectionMapDetail.getMaps();
				processObj = connectionMapDetail.getProcessObj();
			}

			if (null != connectionObj && null != maps && null != processObj) {
				FileUploadParams fileUploadParams = FileUploadParams.builder()
						.senderPartnerId((null != connectionMapDetail.getSenderPartnerId()
								&& connectionMapDetail.getSenderPartnerId() > 0)
										? connectionMapDetail.getSenderPartnerId().toString()
										: null)
						.lstLogDetails(new ArrayList<BpiLogDetail>())
						.receiverPartnerId((null != connectionMapDetail.getReceiverPartnerId()
								&& connectionMapDetail.getReceiverPartnerId() > 0)
										? String.valueOf(connectionMapDetail.getReceiverPartnerId())
										: null)
						.processFlowId(connectionMapDetail.getProcessFlowId()).senderIsaId(processObj.getSenderIsa())
						.receiverIsaId(processObj.getReceiverIsa()).trnType(maps.getTransactionType())
						.trnType(maps.getTransactionType()).actualFileType(maps.getFileType()).ediStandard("X12")
						.createdBy(connectionMapDetail.getCreatedBy()).build();
				log.info("SchedulerBatchJobExecutionId is : " + connectionMapDetail.getSchedulerBatchJobExecutionId());
				processExecutionLog = ProcessExecutionLog.builder()
						.bpiSchedulerBatchJobExecutionId(connectionMapDetail.getSchedulerBatchJobExecutionId())
						.processExecutionId(commonServiceImpl.getProcessExecutionId())
						.partnerId(connectionObj.getPartnerId()).processId(processObj.getId())
						.senderPartnerId(connectionMapDetail.getSenderPartnerId())
						.receiverPartnerId(connectionMapDetail.getReceiverPartnerId()).senderPtnrDispName(null)
						.receiverPtnrDispName(null).ediType(maps.getEdiType()).ediVersion(maps.getEdiVersion())
						.startTime(new Timestamp(new Date().getTime())).build();
				log.info("processExecutionLog is : " + processExecutionLog.toString());
				CintapMDC.putPFMDC(String.valueOf(connectionObj.getPartnerId()), processObj.getId(),
						connectionObj.getConnectionId(), maps.getId());
				log.warn("PROCESS DETAILS :: PROCESS_ID - " + processObj.getId() + "; CONNECTION_ID - "
						+ connectionObj.getConnectionId() + "; MAPS_ID - " + maps.getId());
				fileUploadParams.setEdiType(maps.getEdiType());
				fileUploadParams.setProcessId(processObj.getId());
				commonLogList.add(
						commonServiceImpl.buildBpiLogDetail(String.valueOf(processObj.getId()), LOGTYPE.INFO.getValue(),
								"Process Details : Process ID - " + processObj.getId() + "; Connection ID - "
										+ connectionObj.getConnectionId() + "; Map ID - " + maps.getId()
										+ " Initiated"));

				if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
					ftpClient = ftpConnectionService.getFtpConnection(connectionObj);
					isConnectionSuccess = null != ftpClient ? Boolean.TRUE : Boolean.FALSE;
				} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
					sftpClient = sftpConnectionService.getSFtpConnection(connectionObj);
					isConnectionSuccess = null != sftpClient ? Boolean.TRUE : Boolean.FALSE;
				}
				log.info("is Connection successful? : " + isConnectionSuccess);
				commonLogList.add(commonServiceImpl.buildBpiLogDetail(String.valueOf(processObj.getId()),
						LOGTYPE.INFO.getValue(), "Connection Type: " + connectionObj.getType()));
				// FTP connection is success and pull other details
				if (isConnectionSuccess) {
					commonLogList.add(commonServiceImpl.buildBpiLogDetail(String.valueOf(processObj.getId()),
							LOGTYPE.INFO.getValue(), TRANSPORTLOG.FTP_CONNECTION_SUCCESS.getValue()));
					try {
						if (maps != null && CintapBpiConstants.PROCESS_FLOW_OUTBOUND
								.equalsIgnoreCase(maps.getDirection().toUpperCase().trim())) {
							log.info("Generating OUTBOUND file : ");
							commonLogList.add(commonServiceImpl.buildBpiLogDetail(String.valueOf(processObj.getId()),
									LOGTYPE.INFO.getValue(), TRANSPORTLOG.PROCESS_OUTBOUND.getValue()));
							processExecutionLog.setInboundOutboundInd("O");
							processExecutionLog.setEdiType(maps.getTransactionType());
							log.info("Fetchinf non generated Inbound file details, getSenderPartnerId : "
									+ fileUploadParams.getSenderPartnerId() + " getReceiverPartnerId : "
									+ fileUploadParams.getReceiverPartnerId() + " getTrnType : "
									+ fileUploadParams.getTrnType() + " and getFileType is : "
									+ fileUploadParams.getActualFileType());

							Optional<List<TransactionLog>> transactionLogListOpt = null;
							
							if (EDIFACTTRANSACTIONTYPE.APERAK.getValue().equalsIgnoreCase(maps.getEdiType())) {
								String fileType = (FILETYPE.EDI_FACT.getValue().equals(maps.getFileType()))?"rdy":maps.getFileType();
								fileUploadParams.setFileType(fileType);	
								standardOutboundAperakService.processOutboundRequest(connectionObj, fileUploadParams);
								
							} else {
								transactionLogListOpt = transactionLogRepository
										.findByStpIdAndRtpIdAndTransactionTypeAndStatusId(
												fileUploadParams.getSenderPartnerId(),
												fileUploadParams.getReceiverPartnerId(), fileUploadParams.getTrnType(),
												CINTAPBPISTATUS.NEW.getStatusId());

								if (transactionLogListOpt.isPresent()) {
									List<TransactionLog> transactionLogList = transactionLogListOpt.get();
									log.info("transactionLogList size fetched from DB is : "
											+ transactionLogList.size());
									if (!transactionLogList.isEmpty()) {
										for (TransactionLog transactionLog : transactionLogList) {
											log.info("Generating for File for Inbound BPI Transaction Log Id is : "
													+ transactionLog.getBpiLogId());

											try {
												processExecutionLog
														.setInboundFileExecutionId(transactionLog.getBpiLogId());
												fileUploadParams.setDirection(DIRECTION.OUTBOUND.getDirection());
												fileUploadParams.setLstLogDetails(new ArrayList<>(commonLogList));
												fileUploadParams.setBpiHeaderLogId(null);
												fileUploadParams.setBpiLogId(transactionLog.getBpiLogId());
												fileUploadParams.setFileType(maps.getFileType());
												fileUploadParams = dumpLogDetails(fileUploadParams);

												transactionLog.setStatusId(CINTAPBPISTATUS.IN_PROGRESS.getStatusId());
												transactionLogRepository.save(transactionLog);
												generateOutboundFile(transactionLog, processExecutionLog, connectionObj,
														maps, processObj.getId(), fileUploadParams);

											} catch (Exception e) {
												e.printStackTrace();
												log.info("Exception occured while processing Transaction Log Id is : "
														+ transactionLog.getBpiLogId());
												processExecutionLog
														.setInboundFileExecutionId(transactionLog.getBpiLogId());
												commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
														LOGTYPE.ERROR.getValue(),
														"Exception occured for - " + transactionLog.getBpiLogId()
																+ "Exception is : " + e.getMessage(),
														processObj.getId());
												processExecutionLog.setBatchStatus(
														"Exception occured while processing Transaction Log Id is : "
																+ transactionLog.getBpiLogId());
												processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
												processExecutionLogRepository.save(processExecutionLog);
												processExecutionLog.setId(null);
											}

											// transactionLog.setStatusId(CINTAPBPISTATUS.IN_PROGRESS.getStatusId());
											// transactionLogRepository.save(transactionLog);
											// processExecutionLog.setInboundFileExecutionId(transactionLog.getBpiLogId());
											// generateOutboundFile(transactionLog, processExecutionLog, connectionObj,
											// maps,
											// fileUploadParams.getBpiHeaderLogId(), processObj.getId());
										}
									}
								} else {
									processExecutionLog.setBatchStatus(
											"No inbound records present in DB to generate the outbound ");
									processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
									processExecutionLogRepository.save(processExecutionLog);
									processExecutionLog.setId(null);
								}
							}

						} else if (maps != null && CintapBpiConstants.PROCESS_FLOW_INBOUND
								.equalsIgnoreCase(maps.getDirection().toUpperCase().trim())) {
							commonLogList.add(commonServiceImpl.buildBpiLogDetail(String.valueOf(processObj.getId()),
									LOGTYPE.INFO.getValue(), TRANSPORTLOG.PROCESS_INBOUND.getValue()));
							String[] listOfFiles = null;

							if (null != ftpClient) {
								log.info("FTP : Fetching files ");
								listOfFiles = ftpClient.listNames(connectionObj.getSourceDirectory());
							}

							if (null != sftpClient) {
								Vector<LsEntry> fileList = sftpClient.ls(connectionObj.getSourceDirectory());
								log.info("fileList is : " + fileList.toString());
								listOfFiles = new String[fileList.size() + 2];
								listOfFiles[0] = ".";
								listOfFiles[1] = "..";
								for (int i = 0; i < fileList.size(); i++) {
									LsEntry entry = (LsEntry) fileList.get(i);
									listOfFiles[i + 2] = entry.getFilename();
								}
							}

							Integer fileCount = null == listOfFiles ? 0 : listOfFiles.length - 2;
							if (null != listOfFiles) {
								StringJoiner fileJoiner = fetchFileNames(listOfFiles);
								log.warn("FTP NO OF FILES : " + fileCount);
								commonLogList
										.add(commonServiceImpl.buildBpiLogDetail(String.valueOf(processObj.getId()),
												LOGTYPE.INFO.getValue(), "No of files found : " + fileCount));// + ";
																												// File
																												// Names
																												// : "+
																												// fileJoiner.toString()));
								fileUploadParams.setPartnerId(String.valueOf(connectionObj.getPartnerId()));
								fileUploadParams.setEdiType(maps.getEdiType());
								fileUploadParams.setEdiVersion(maps.getEdiVersion());
								fileUploadParams.setFileCount(fileCount);
								processExecutionLog.setFileCount(fileCount);
								processExecutionLog.setInboundOutboundInd("I");
								processExecutionLog.setEdiType(maps.getEdiType());
							}

							if (null == listOfFiles || listOfFiles.length == 2) {
								log.info(
										"No Files present under the directory : " + connectionObj.getSourceDirectory());
								processExecutionLog.setBatchStatus(
										"No Files present under the directory : " + connectionObj.getSourceDirectory());
								processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
								processExecutionLogRepository.save(processExecutionLog);
								processExecutionLog.setId(null);
							} else {
								for (String fileName : listOfFiles) {
									log.info("FILE NAME : " + fileName);
									try {
										String fileNameWithoutPath = FilenameUtils.getName(fileName);
										if (!fileNameWithoutPath.equals(".") && !fileNameWithoutPath.equals("..")) {
											String fileNameToCheck = null;
											String fNames[] = fileName.split("\\.");
											if (fNames.length > 2) {
												for (int fileNameCount = 0; fileNameCount < (fNames.length
														- 1); fileNameCount++) {
													fileNameToCheck = fileNameToCheck + fNames[fileNameCount];
												}
											} else {
												fileNameToCheck = fNames[0];
											}
											String commonNames[] = connectionObj.getFileName().split("\\*");
											log.info("commonNames : " + commonNames[0] + ", fileNameToCheck : "
													+ fileNameToCheck);
											if (connectionObj.getFileName().startsWith("*.")
													|| fileNameToCheck.contains(commonNames[0])) {
												fileUploadParams.setFileType(FilenameUtils.getExtension(fileName));
												fileUploadParams.setFileName(fileName);
												processExecutionLog.setFileName(fileName);
												processExecutionLog.setFileType(FilenameUtils.getExtension(fileName));
												processExecutionLog
														.setFileExecutionId(commonServiceImpl.getFileExecutionId());
												fileUploadParams.setDirection(DIRECTION.INBOUND.getDirection());
												fileUploadParams.setLstLogDetails(new ArrayList<>(commonLogList));
												fileUploadParams.setBpiHeaderLogId(null);
												fileUploadParams = dumpLogDetails(fileUploadParams);
												log.info("File name is : " + FilenameUtils.getName(fileName));
												String fileData = null;

												if (CONNECTIONTYPE.FTP.getConnection()
														.equalsIgnoreCase(connectionObj.getType())) {
													fileData = ftpFileRead(ftpClient, connectionObj,
															FilenameUtils.getName(fileName));
												} else if (CONNECTIONTYPE.SFTP.getConnection()
														.equalsIgnoreCase(connectionObj.getType())) {
													fileData = sftpFileRead(sftpClient, connectionObj, fileName);
												}
												if (null != fileData) {
													processInboundFile(processObj, connectionObj, maps, fileData,
															fileNameWithoutPath, fileUploadParams, processExecutionLog);
												} else {
													log.info("Unable to read File Data");
												}
											}
										}
									} catch (Exception e) {
										log.info("Exception occured while procesing the file : " + fileName);
										e.printStackTrace();
										log.info("Exception occured is : " + e);
										commonServiceImpl
												.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
														LOGTYPE.INFO.getValue(), "Exception occured for -" + fileName
																+ " " + "Exception is : " + e.getMessage(),
														processObj.getId());
										processExecutionLog.setBatchStatus(
												"GOT EXCEPTION WHILE PROCESSING FILE : " + e.getMessage());
										processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
										processExecutionLogRepository.save(processExecutionLog);
										processExecutionLog.setId(null);
									}
								}
							}
						}

					} catch (IOException e) {
						transportCommonUtility.saveErrorLog(e);
						log.warn("Batch process : got exception while processing.", e);
						processExecutionLog.setBatchStatus("Exception occured : " + e.getMessage());
						processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
						processExecutionLogRepository.save(processExecutionLog);
						processExecutionLog.setId(null);
					} finally {
						try {
							if (null != ftpClient || null != sftpClient) {
								commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
										LOGTYPE.INFO.getValue(), TRANSPORTLOG.FTP_CONNECTION_CLOSED.getValue(),
										processObj.getId());
								if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
									ftpClient.logout();
								} else if (CONNECTIONTYPE.SFTP.getConnection()
										.equalsIgnoreCase(connectionObj.getType())) {
									sftpClient.disconnect();
								}
							}

						} catch (Exception e) {
							log.warn("Batch process : got exception while processing.", e);
							transportCommonUtility.saveErrorLog(e);
						}
					}
				} else {
					commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
							TRANSPORTLOG.FTP_CONNECTION_FAILED.getValue(), processObj.getId());
					log.warn("PROCESS FLOW :: Unable to connect to : " + connectionObj.getType());

					processExecutionLog
							.setBatchStatus("PROCESS FLOW :: Unable to connect to : " + connectionObj.getType());
					processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
					processExecutionLogRepository.save(processExecutionLog);
					processExecutionLog.setId(null);
				}
			}
			CintapMDC.clearMDC();
		} catch (Exception e) {
			transportCommonUtility.saveErrorLog(e);
			log.info("Exception occured : " + e);
			if (null != processExecutionLog) {
				processExecutionLog.setBatchStatus("Exception occured : " + e.getMessage());
				processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
				processExecutionLogRepository.save(processExecutionLog);
				processExecutionLog.setId(null);
			}
		} finally {
			if (null != connectionMapDetail && null != connectionMapDetail.getSchedulerBatchJobExecutionId()
					&& null != connectionMapDetail.getProcessObj().getRespQueueName()) {
				pushToProcessStatusToBPI(connectionMapDetail.getProcessObj().getRespQueueName(),
						connectionMapDetail.getSchedulerBatchJobExecutionId());
				log.info("Response sent to Response queue : " + connectionMapDetail.getProcessObj().getRespQueueName());
			}
		}

	}

	private void pushToProcessStatusToBPI(String queue, Integer schedulerBatchJobExecutionId) {
		ProcessStatus statusMsg = ProcessStatus.builder().schedulerBatchJobExecutionId(schedulerBatchJobExecutionId)
				.status("completed").build();
		String msg = TransportCommonUtility.convertObjectToJson(statusMsg);
		kafkaSender.send(queue, msg);
		log.info("Pushed to Queue : " + msg + ", and queue name is : " + queue);
	}

	private FileUploadParams dumpLogDetails(FileUploadParams fileUploadParams) {
		BpiLogHeader bpiHeaderLog = null;
		if (!EDIFACTTRANSACTIONTYPE.APERAK.getValue().equalsIgnoreCase(fileUploadParams.getTrnType())) {
			bpiHeaderLog = commonServiceImpl.saveBpiHeader(fileUploadParams);

			fileUploadParams.setBpiHeaderLogId(bpiHeaderLog.getLogHdrId());
			fileUploadParams.setGroupId(bpiHeaderLog.getGroupId());
			for (BpiLogDetail bpiLogDetail : fileUploadParams.getLstLogDetails()) {
				bpiLogDetail.setLogDetailId(null);
				log.info("bpiLogDetail saving  is : " + bpiLogDetail);
				bpiLogDetail.setLogHeaderId(bpiHeaderLog.getLogHdrId());
				bpiLogDetailRepository.save(bpiLogDetail);
				log.info("bpiLogDetail saved is : " + bpiLogDetail);
			}
		}
		return fileUploadParams;
	}

	private StringJoiner fetchFileNames(String[] listOfFiles) {
		StringJoiner fileJoiner = new StringJoiner("");
		for (String fileName : listOfFiles) {
			if (StringUtils.isNoneEmpty(fileName) && fileName.length() > 3) {
				fileJoiner.add(fileName + "  ");
			}
		}
		return fileJoiner;
	}

	private void generateOutboundFile(TransactionLog transactionLog, ProcessExecutionLog processExecutionLog,
			Connection connectionObj, Maps maps, Integer processId, FileUploadParams fileUploadParams) {
		Integer batchId = null;

		String targetFilepath = null;
		String fileType = "";
		String convertedFileData = null;
		Integer bpiHeaderLogId = fileUploadParams.getBpiHeaderLogId();
		try {
			batchId = transactionLog.getBpiLogId();
			commonServiceImpl.saveBpiLogDetail(bpiHeaderLogId, LOGTYPE.INFO.getValue(),
					"Initiated OUTBOUND File Generation for Batch Id: " + batchId, processId);
			if (null != transactionLog) {
				log.info("need to handle Outbound related ");
			}

			fileType = fileUploadParams.getFileType();// FILETYPE.XML.getValue();
			commonServiceImpl.saveBpiLogDetail(bpiHeaderLogId, LOGTYPE.INFO.getValue(),
					"OUTBOUND " + fileType + " file generation for transaction id: " + transactionLog.getBpiLogId(),
					processId);
			if (FILETYPE.XML.getValue().equals(fileType)) {
				convertedFileData = standardOutboundFileProcessService.processXMLFile(transactionLog, fileUploadParams);
			} else if (FILETYPE.EDI_FACT.getValue().equals(fileType)) {
				convertedFileData = standardOutboundFileProcessService.processEdifactFile(transactionLog,
						fileUploadParams);
				if (EDIFACTTRANSACTIONTYPE.IFTSTA.getValue().equals(fileUploadParams.getEdiType())) {
					serialNumberService.generateSerialNumberFile(transactionLog, connectionObj);
				}
			}
			fileType = (FILETYPE.EDI_FACT.getValue().equals(maps.getFileType()))?"rdy":maps.getFileType();
			commonServiceImpl.saveBpiLogDetail(bpiHeaderLogId, LOGTYPE.INFO.getValue(),
					"OUTBOUND " + fileType + " file generated : " + convertedFileData, processId);
			String fileName = connectionObj.getFileName() + "_" + transactionLog.getStpTransId() + "_" +  batchId + "_"
					+ getFileDateFormatInCS() + "." + fileType;
			targetFilepath = connectionObj.getTargetDirectory() + "" + fileName;

			if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
				ftpFileOperationsService.moveFileToTarget(connectionObj, connectionObj.getFileName(), convertedFileData,
						targetFilepath);
			} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
				sFtpFileOperationsService.moveFileToTarget(connectionObj, connectionObj.getFileName(),
						convertedFileData, targetFilepath);
			}
			commonServiceImpl.saveBpiLogDetail(bpiHeaderLogId, LOGTYPE.INFO.getValue(),
					"OUTBOUND " + fileType + " file generated location : " + targetFilepath, processId);
			if (!StringUtils.isEmpty(convertedFileData)) {
				transactionLog.setStatusId(CINTAPBPISTATUS.PROCESSED.getStatusId());
				transactionLogRepository.save(transactionLog);

				fileUploadParams.setFileType(fileType);
				fileUploadParams.setRawFile(convertedFileData);
				fileUploadParams.setFileName(fileName);

//				TransactionLogInboundOutbound transactionLogInboundOutbound = transportCommonBuilder
//						.buildTransactionLogInboundOutbound(fileUploadParams);
				transactionLogInboundOutboundRepository.updateSentRawFile(convertedFileData, fileUploadParams.getBpiLogId(), fileUploadParams.getTrnType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception occured : " + e);
			transportCommonUtility.saveErrorLog(e);
			processExecutionLog.setStatusCode("99");
			processExecutionLog.setBatchStatus("Exception occured : " + e.getMessage());
			processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
			processExecutionLogRepository.save(processExecutionLog);
			transactionLog.setStatusId(CINTAPBPISTATUS.ERROR.getStatusId());
			transactionLogRepository.save(transactionLog);
		}
	}

	public static String getFileDateFormatInCS() {
		SimpleDateFormat format = new SimpleDateFormat(CintapBpiConstants.DATE_DDMMYYYYHHMMSS);
		format.setTimeZone(TimeZone.getTimeZone("CST"));
		return format.format(new Date());
	}

	private String ftpFileRead(FTPClient ftpClient, Connection connectionObj, String fileName) throws Exception {
		try {
			log.info("FTP File Processing :: Full File name is  : "
					+ (connectionObj.getSourceDirectory() + "" + fileName));

			InputStream inputStream = ftpClient.retrieveFileStream(connectionObj.getSourceDirectory() + "" + fileName);
			log.info("is inputStream null ? " + (null != inputStream ? "no" : "yes") + " and reason is : "
					+ ftpClient.completePendingCommand());
			InputStream is = ftpClient.retrieveFileStream(connectionObj.getSourceDirectory() + "" + fileName);
			log.info("is inputStream null ? " + (null != inputStream ? "no" : "yes") + " and reason is : "
					+ ftpClient.completePendingCommand());
			log.info("Reason code is : " + ftpClient.getReplyString());
			// byte[] bytes = toByteArray(inputStream)
			String sourceFileData = xmlFileFormatCovert.validateFileDataAndRemoveBom(inputStream, is);
			return sourceFileData;
		} catch (IOException e) {
			transportCommonUtility.saveErrorLog(e);
			log.info("Error while reading File from FTP");
			return "";
		}
	}

	private String sftpFileRead(ChannelSftp sftpClient, Connection connectionObj, String fileName) throws Exception {
		InputStream inputStream = null;
		try {
			log.info("SFTP file processing :: Full File name is  : "
					+ (connectionObj.getSourceDirectory() + "" + fileName));
			inputStream = sftpClient.get(connectionObj.getSourceDirectory() + "" + fileName);

			log.info("is inputStream null ? " + (null != inputStream ? "no" : "yes") + " and reason is : "
					+ sftpClient.stat(connectionObj.getSourceDirectory() + "" + fileName));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			inputStream.transferTo(baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());

			// byte[] bytes = toByteArray(inputStream)
			String sourceFileData = xmlFileFormatCovert.validateFileDataAndRemoveBom(inputStream, is);
			return sourceFileData;
		} catch (SftpException e) {
			e.printStackTrace();
			log.info("Error while reading File from FTP");
			transportCommonUtility.saveErrorLog(e);
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			log.info("Error while reading File from FTP");
			transportCommonUtility.saveErrorLog(e);
			throw e;
		} finally {
			if (null != inputStream) {
				log.info("Closing Input stream ");
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void processInboundFile(com.cintap.transport.message.model.Process processObj, Connection connectionObj,
			Maps maps, String fileData, String fileName, FileUploadParams fileUploadParams,
			ProcessExecutionLog processExecutionLog) throws Exception {
		Integer bpiLogId = null;
		String sourceFilepath = "";
		String targetFilepath = "";
		String sourceFileData = "";
		String fileType = "";
		String errorFilePath = "";
		fileUploadParams.setBatchId(processExecutionLog.getFileExecutionId());
		try {
			sourceFileData = fileData;
			fileType = FilenameUtils.getExtension(fileName);
			sourceFilepath = connectionObj.getSourceDirectory() + "" + fileName;
			log.warn("SENDING REQUEST TO PROCESS FILE :: PARTNER ID - " + connectionObj.getPartnerId() + " EDI TYPE - "
					+ maps.getEdiType() + "EDI VERSION - " + maps.getEdiVersion() + "  FILE EXTENSION : " + fileType
					+ " TRANSACTION TYPE : " + maps.getTransactionType());
			fileUploadParams.setSource(connectionObj.getType());
			fileUploadParams.setRawFile(sourceFileData);
			processExecutionLog.setRawFile(sourceFileData);
			fileUploadParams.setFileName(fileName);
			processExecutionLog.setSourceType(connectionObj.getType());

			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
					fileName + " " + TRANSPORTLOG.FILE_PROCESS_INITIATED.getValue(), processObj.getId());

			/**
			 * Parsing EDI file from Core EDI Library
			 */
			Response response = null;
			// TranspotationX12ParserServiceImpl transpotationX12ParserService = new
			// TranspotationX12ParserServiceImpl();
			// response = transpotationX12ParserService.parse214(sourceFileData);
			// log.info("Response ::
			// "+TransportCommonUtility.convertObjectToJson(response));

			/**
			 * Set ACK 997 response to fileUploadParams
			 */
			// String ack997 = null!=response?response.getEdiAck():"";
			// fileUploadParams.setAck997(ack997);

			if (FILETYPE.EDI.getValue().equalsIgnoreCase(maps.getFileType())) {
				fileUploadParams.setEdiType(maps.getEdiType());
				fileUploadParams.setFileType(FILETYPE.EDI.getValue());
				// fileUploadParams = commonServiceImpl.validateInboundS2696File(sourceFileData,
				// fileUploadParams);
				fileUploadParams = standardInboundShipmentTrackingEdiService.processShipmentTracking(response,
						fileUploadParams);
			} else if (FILETYPE.EDI_FACT.getValue().equalsIgnoreCase(maps.getFileType())) {
				fileUploadParams.setEdiType(maps.getEdiType());
				fileUploadParams.setTrnType(maps.getTransactionType());
				fileUploadParams.setFileType(FILETYPE.EDI_FACT.getValue());
				fileUploadParams = standardInboundFileProcessService.processEdifactFile(sourceFileData,
						fileUploadParams, processExecutionLog);
			} else if (FILETYPE.XML.getValue().equalsIgnoreCase(maps.getFileType())) {
				fileUploadParams.setEdiType(maps.getEdiType());
				fileUploadParams.setTrnType(maps.getTransactionType());
				fileUploadParams.setFileType(FILETYPE.XML.getValue());
				fileUploadParams = standardInboundFileProcessService.processXMLFile(sourceFileData, fileUploadParams,
						processExecutionLog);
			}

			bpiLogId = fileUploadParams.getBpiLogId();
			processExecutionLog.setEdiType(fileUploadParams.getEdiType());
			log.warn("FILE PROCESSED AND TRANSACTION LOG ID IS: " + bpiLogId);

			/*
			 * Creating Functional ACK 997 response in the configured location if it is
			 * enabled at Process level
			 * 
			 * If value is 1, then create ACK997
			 * 
			 * If value is 0, then don't create ACK997
			 * 
			 */
			// Creating Functional ACK 997 response in the source location
			String fileNameWithoutExt = FilenameUtils.getName(fileName);
			fileNameWithoutExt = fileNameWithoutExt.replaceFirst("[.][^.]+$", "");

			if (null != processObj.getAck997Flag() && 1 == processObj.getAck997Flag()) {
				createAck997File(bpiLogId, processObj, connectionObj, fileNameWithoutExt, fileUploadParams);
			} else {
				log.info("ACK 997 is not enabled");
			}

			if (null != fileUploadParams.getBpiLogId() && fileUploadParams.getBpiLogId() > 0) {

				if (!StringUtils.isEmpty(connectionObj.getArchivePath())) {

					boolean isFileMoved = false;

					targetFilepath = connectionObj.getArchivePath() + "" + FilenameUtils.getName(fileName) + "_"
							+ bpiLogId + "_" + TransportCommonUtility.getFileDateFormatInCS() + "." + fileType;

					if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
						isFileMoved = ftpFileOperationsService.moveFtpFile(connectionObj, fileName, sourceFilepath,
								targetFilepath);
					} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
						isFileMoved = sFtpFileOperationsService.moveFtpFile(connectionObj, fileName, sourceFilepath,
								targetFilepath);
					}

					commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
							TRANSPORTLOG.FILE_PROCESS_ARCH_INITIATED.getValue(), processObj.getId());

					if (isFileMoved) {
						commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
								LOGTYPE.INFO.getValue(), "File successfully archived from source file path : "
										+ sourceFilepath + "; to target file path : " + targetFilepath,
								processObj.getId());
					} else {
						commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
								LOGTYPE.INFO.getValue(), "Unable to archive file from soruce file path : "
										+ sourceFilepath + "; to target file path : " + targetFilepath,
								processObj.getId());
					}
				} else {
					log.info("Unable to find Archive File Path from Connection");
				}
				fileUploadParams.setTrnStatus(CintapBpiConstants.SUCCESS);
				processExecutionLog.setStatusCode("00");
				processExecutionLog.setBatchStatus("File processed successfully ");
				processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
				processExecutionLogRepository.save(processExecutionLog);
				processExecutionLog.setId(null);
			}
		} catch (InvalidEdiFileException e) {
			log.info("GOT EXCEPTION WHILE PROCESSING FILE :: {}", e);
			transportCommonUtility.saveErrorLog(e);
			errorFilePath = connectionObj.getErrorPath() + "" + fileName + "_" + bpiLogId + "_"
					+ TransportCommonUtility.getCurrentDateTime() + "." + fileType;

			if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
				ftpFileOperationsService.moveFtpFile(connectionObj, fileName, sourceFilepath, errorFilePath);
			} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
				sFtpFileOperationsService.moveFtpFile(connectionObj, fileName, sourceFilepath, errorFilePath);
			}
			if (fileUploadParams.getBatchId() != null && fileUploadParams.getBatchId() > 0) {
				commonServiceImpl.udpateBatchTransactionLogStatus(fileUploadParams.getBatchId(), null,
						e.getErrorCode() + " : " + e.getErrorMessage(), CINTAPBPISTATUS.ERROR.getStatusId(), true);
			}
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());
			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
					CintapBpiConstants.FAIL + "; " + e.getErrorMessage() + "; For the file : " + fileName,
					processObj.getId());

			fileUploadParams.setTrnStatus("ERROR");
			processExecutionLog.setBatchStatus("GOT EXCEPTION WHILE PROCESSING FILE : " + e.getMessage());
			processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
			processExecutionLogRepository.save(processExecutionLog);
			processExecutionLog.setId(null);
		} catch (Exception e) {
			log.info("GOT EXCEPTION WHILE PROCESSING FILE :: {}", e);
			transportCommonUtility.saveErrorLog(e);
			errorFilePath = connectionObj.getErrorPath() + "" + fileName + "_" + bpiLogId + "_"
					+ TransportCommonUtility.getCurrentDateTime() + "." + fileType;
			log.info("errorFilePath is : " + errorFilePath);

			if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
				ftpFileOperationsService.moveFtpFile(connectionObj, fileName, sourceFilepath, errorFilePath);
			} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
				sFtpFileOperationsService.moveFtpFile(connectionObj, fileName, sourceFilepath, errorFilePath);
			}
			if (fileUploadParams.getBatchId() != null && fileUploadParams.getBatchId() > 0) {
				commonServiceImpl.udpateBatchTransactionLogStatus(fileUploadParams.getBatchId(), null, e.getMessage(),
						CINTAPBPISTATUS.ERROR.getStatusId(), true);
			}
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());
			commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
					CintapBpiConstants.FAIL + "; " + e + "; For the file : " + fileName, processObj.getId());

			fileUploadParams.setTrnStatus("ERROR");
			processExecutionLog.setBatchStatus("GOT EXCEPTION WHILE PROCESSING FILE : " + e.getMessage());
			processExecutionLog.setEndTime(new Timestamp(new Date().getTime()));
			processExecutionLogRepository.save(processExecutionLog);
			processExecutionLog.setId(null);
		}
	}

	/**
	 * 
	 * @param batchId
	 * @param processObj
	 * @param connectionObj
	 * @param fileName
	 * @param fileUploadParams
	 * @param fileProcessResponse
	 * 
	 *                            Generate ACK997 file in the given FTP or SFTP
	 *                            location
	 * 
	 */
	private void createAck997File(Integer bpiLogId, com.cintap.transport.message.model.Process processObj,
			Connection connectionObj, String fileName, FileUploadParams fileUploadParams) {
		if (StringUtils.isNoneEmpty(fileUploadParams.getAck997())) {
			String ackFilePath = null;
			try {
				if (null != bpiLogId) {
					ackFilePath = connectionObj.getAck997Path() + fileName + "_" + bpiLogId + "_997" + "_"
							+ TransportCommonUtility.getCurrentDateTime() + "." + ackFileNameExtension;
				} else {
					ackFilePath = connectionObj.getAck997Path() + fileName + "_997" + "_"
							+ TransportCommonUtility.getCurrentDateTime() + "." + ackFileNameExtension;
				}
				log.info("Creating ACK997 File Location : " + ackFilePath);

				if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
					ftpFileOperationsService.createFile(connectionObj, ackFilePath, fileUploadParams.getAck997());
				} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
					// Connection connectionObj,String fileName,String fileData,String source
					sFtpFileOperationsService.moveFileToTarget(connectionObj, "", fileUploadParams.getAck997(),
							ackFilePath);
				}

				commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						"ACK997 File path : " + ackFilePath + ";  ACK 997 File successfully generated : "
								+ fileUploadParams.getAck997(),
						processObj.getId());
			} catch (Exception e) {
				transportCommonUtility.saveErrorLog(e);
				commonServiceImpl.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						"Bpi Log Id: " + bpiLogId + "; Unable to generate ACK 997 File in the given file path : "
								+ ackFilePath + "; Due to : " + e.getMessage(),
						processObj.getId());
				log.info("Unable to Generate ACK 997 for Bpi log id : " + bpiLogId);
			}
		}
	}

	private static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}
}
