package com.cintap.transport.common.sftp.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.cintap.transport.common.message.model.Connection;
import com.jcraft.jsch.ChannelSftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SFtpFileOperationsService {

	@Autowired
	SftpConnectionService sftpConnectionService;

	public boolean moveFtpFile(Connection connectionObj, String fileName, String source, String target)
			throws Exception {
		String respMessage = null;
		boolean isFileMoved = false;
		ChannelSftp channelSftp = null;
		try {
			channelSftp = sftpConnectionService.getSFtpConnection(connectionObj);
			log.info("source is : " + source + " , target is : " + target);
			channelSftp.rename(source, target);
			respMessage = source + " Target PATH : " + target;
			log.info("INBOUND : File moved to target folder. SOURCE PATH : {}", respMessage);
			isFileMoved = true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Got exception while moving file. SOURCE PATH : {}", source);
		} finally {
			try {
				channelSftp.exit();
			} catch (Exception e) {
				log.error("FtpFileOperations {}", e);
			}
		}
		return isFileMoved;
	}

	/**
	 * 
	 * @param connectionObj
	 * @param fileName
	 * @param fileData      --> File content
	 * @param source        --> File destination location where file to be created
	 */
	public void moveFileToTarget(Connection connectionObj, String fileName, String fileData, String destinationPath) {
		ChannelSftp channelSftp = null;
		try {
			channelSftp = sftpConnectionService.getSFtpConnection(connectionObj);
			InputStream stream = new ByteArrayInputStream(fileData.getBytes(StandardCharsets.UTF_8));
			log.info("uploadfing file to : " + destinationPath);
			channelSftp.put(stream, destinationPath);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Got exception while moving file. SOURCE PATH : " + destinationPath + ";  " + e.getMessage());
		} finally {
			try {
				channelSftp.disconnect();
			} catch (Exception e) {
				log.error("FtpFileOperations {}", e);
			}
		}
	}

	private InputStream convertStringToInputStreamCommonIO(String fileData) {
		return IOUtils.toInputStream(fileData, StandardCharsets.UTF_8);
	}

	public void uploadLocalFile(Connection connectionObj, String filePath, String targetFilepath) throws Exception {
		ChannelSftp channelSftp=null;
		try {
			channelSftp = sftpConnectionService.getSFtpConnection(connectionObj);
            log.info("Start uploading local file");
            channelSftp.put(filePath, targetFilepath);
            File localFile = new File(filePath);
//            localFile.delete();
            log.info("The file is uploaded successfully : "+targetFilepath);
		}finally {
			try{channelSftp.exit();}catch(Exception e) {log.error("FtpFileOperations {}",e);}
		}
	}

}
