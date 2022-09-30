package com.cintap.transport.common.ftp.service;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

import com.cintap.transport.common.message.model.Connection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FtpFileOperationsService {
	private final Logger appLogger = LoggerFactory.getLogger(FtpFileOperationsService.class);
	public boolean moveFtpFile(Connection connectionObj,String fileName,String source,String target) {
		boolean isFileMoved=false;
		FTPClient ftpClient = new FTPClient();
		String respMessage=null;
		try {
			ftpClient.connect(connectionObj.getHost(), connectionObj.getPortNumber());
			ftpClient.login(connectionObj.getUserName(),connectionObj.getPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(51200); // 1024
			isFileMoved = ftpClient.rename(source,target);
			respMessage = source+" Target PATH : "+target;
			if(isFileMoved) {
				appLogger.info("INBOUND : File moved to target folder. SOURCE PATH : {}",respMessage);
			}else {
				appLogger.info("INBOUND : Unable Archive file. SOURCE PATH : {}",respMessage);
			}
		}catch(Exception e) {
			appLogger.info("Got exception while moving file. SOURCE PATH : {}",respMessage);
		}finally {
			try{ftpClient.logout();}catch(Exception e) {appLogger.error("FtpFileOperations {}",e);}
		}
		return isFileMoved;
	}
	
	public void createFile(Connection connectionObj, String fileName, String fileData) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(connectionObj.getHost(), connectionObj.getPortNumber());
			ftpClient.login(connectionObj.getUserName(),connectionObj.getPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(51200); // 1024
			
			try (InputStream targetStream = new ByteArrayInputStream(fileData.getBytes())) {
			     ftpClient.storeFile(fileName, targetStream);
			     appLogger.info("File created with name : "+fileName);
			}
			
		}catch(SocketTimeoutException execption) {
			appLogger.info("Exception occured : "+execption);
		}catch(Exception e) {
			appLogger.info("Got exception while moving file. SOURCE PATH : "+connectionObj.getFilePath()+""+fileName+" ARCHIVE PATH : "+connectionObj.getArchivePath()+""+fileName);
		}finally {
			try{ftpClient.logout();}catch(Exception e) {appLogger.error("FtpFileOperations {}",e);}
		}
	}

	public void moveFileToTarget(Connection connectionObj,String fileName,String fileData,String source){
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(connectionObj.getHost(), connectionObj.getPortNumber());
			ftpClient.login(connectionObj.getUserName(),connectionObj.getPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(51200); // 1024
			InputStream fileInputStream = convertStringToInputStreamCommonIO(fileData);
			if(fileInputStream!=null) {
				boolean isFileMoved = ftpClient.storeFile(source,fileInputStream);
				if(isFileMoved) {
					appLogger.info("OUTBOUND: File moved to archive folder");
				}else {
					appLogger.info("OUTBOUND : Unable Archive file. SOURCE PATH : "+connectionObj.getFilePath()+""+fileName+" OUTPUT FILE PATH : "+source+""+fileName);
				}
			}
		}catch(SocketTimeoutException execption) {
			appLogger.info("");
		}catch(Exception e) {
			appLogger.info("Got exception while moving file. SOURCE PATH : "+connectionObj.getFilePath()+""+fileName+" ARCHIVE PATH : "+connectionObj.getArchivePath()+""+fileName);
		}finally {
			try{ftpClient.logout();}catch(Exception e) {appLogger.error("FtpFileOperations {}",e);}
		}
	}

	private InputStream convertStringToInputStreamCommonIO(String fileData) {
		return IOUtils.toInputStream(fileData, StandardCharsets.UTF_8);
	}
	
	public void uploadLocalFile(Connection connectionObj, String FilePath, String targetFilepath) throws Exception {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(connectionObj.getHost(), connectionObj.getPortNumber());
			ftpClient.login(connectionObj.getUserName(),connectionObj.getPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(51200); // 1024
			
			File firstLocalFile = new File(FilePath);
			 
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            appLogger.info("Start uploading first file");
            boolean done = ftpClient.storeFile(targetFilepath, inputStream);
            inputStream.close();
            if (done) {
//            	firstLocalFile.delete();
            	appLogger.info("The file is uploaded successfully : "+targetFilepath);
            }
		}catch(Exception e) {
			appLogger.info("Exception occured while uploading CSV file "+ e);
			throw e;
		}finally {
			try{ftpClient.logout();}catch(Exception e) {appLogger.error("FtpFileOperations {}",e);}
		}
	}
	
}
