/**
 * 
 */
package com.cintap.transport.common.sftp.service;

import com.cintap.transport.common.message.model.Connection;
import com.cintap.transport.common.util.CloudConnectUtility;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class SftpConnectionService {
	
	@Autowired
	CloudConnectUtility cloudConnectUtility;
	
	public ChannelSftp getSFtpConnection(Connection connectionObj) throws Exception {
		Session jschSession = null;
		try {
			/*JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");
			jschSession = jsch.getSession(connectionObj.getUserName(), connectionObj.getHost(), connectionObj.getPortNumber());
			jschSession.setPassword(connectionObj.getPassword());
			jschSession.connect(connectionObj.getConnectionTimeout());
			Channel sftp = jschSession.openChannel("sftp");
			sftp.connect();
			return (ChannelSftp) sftp;*/
			
			JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");
			jschSession = jsch.getSession(connectionObj.getUserName(), connectionObj.getHost(), connectionObj.getPortNumber());
			jschSession.setPassword(connectionObj.getPassword());
			jschSession.connect(connectionObj.getConnectionTimeout());
			ChannelSftp channelSftp = (ChannelSftp)jschSession.openChannel("sftp");
			channelSftp.connect();
			return channelSftp;
		}catch (Exception e) {
			log.error("SFTP CONNECTION ERROR :: ");
			cloudConnectUtility.saveErrorLog(e);
			throw e;
		}
	}
}
