/**
 * 
 */
package com.cintap.transport.common.ftp.service;

import java.io.IOException;

import com.cintap.transport.common.message.model.Connection;
import com.cintap.transport.common.model.ApiResponse;
import com.cintap.transport.common.util.TransportResponseCodes;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class FtpConnectionService {

	public ApiResponse testFtpConnection(Connection connection) throws IOException {
		String server = connection.getHost();
		int port = connection.getPortNumber();
		String user = connection.getUserName();
		String pass = connection.getPassword();
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			showServerReply(ftpClient);
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				log.info("Operation failed. Server reply code: " + replyCode);
				return ApiResponse.builder().statusCode(TransportResponseCodes.ERROR).statusMessage("Operation failed. Server reply code: " + replyCode).build();
			}
			boolean success = ftpClient.login(user, pass);
			showServerReply(ftpClient);
			if (!success) {
				log.info("Could not login to the server");
				return ApiResponse.builder().statusCode(TransportResponseCodes.ERROR).statusMessage("Could not login to the server").build();
			} else {
				log.info("LOGGED IN FTP SERVER");
				return ApiResponse.builder().statusCode(TransportResponseCodes.SUCCESS).statusMessage("LOGGED IN SERVER SUCCESSFULLY").build();
			}
		} catch (IOException ex) {
			log.info("Oops! FTP connection Something wrong happened");
			return ApiResponse.builder().statusCode(TransportResponseCodes.ERROR).statusMessage("Operation failed").build();
		}finally {
			ftpClient.logout();
		}	
	}

	private static void showServerReply(FTPClient ftpClient) {
		String[] replies = ftpClient.getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (String aReply : replies) {
				log.info("SERVER: " + aReply);
			}
		}
	}

	public FTPClient getFtpConnection(Connection connectionObj) throws Exception {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(connectionObj.getHost(), connectionObj.getPortNumber());
			ftpClient.login(connectionObj.getUserName(),connectionObj.getPassword());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(51200);
			return ftpClient;
		}catch (Exception e) {
			log.error("FTP Get connection ERROR :: "+e);
			throw e;
		}
	}

	public ChannelSftp getSFtpConnection(Connection connectionObj) throws Exception {
		Session jschSession = null;
		try {
			JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");
			jschSession = jsch.getSession(connectionObj.getUserName(), connectionObj.getHost(), connectionObj.getPortNumber());
			jschSession.setPassword(connectionObj.getPassword());
			jschSession.connect(connectionObj.getConnectionTimeout());
			Channel sftp = jschSession.openChannel("sftp");
			sftp.connect();
			return (ChannelSftp) sftp;
		}catch (Exception e) {
			log.error("SFTP Get connection ERROR :: "+e);
			throw e;
		}
	}
}
