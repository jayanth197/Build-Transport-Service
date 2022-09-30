package com.cintap.transport.common.enums;

public enum CONNECTIONTYPE {
	FTP("FTP"),
	SFTP("SFTP");
	
	private String connection;
	
	private CONNECTIONTYPE(String connection) {
		this.connection = connection;
	}
	
	public String getConnection() {
		return connection;
	}
}
