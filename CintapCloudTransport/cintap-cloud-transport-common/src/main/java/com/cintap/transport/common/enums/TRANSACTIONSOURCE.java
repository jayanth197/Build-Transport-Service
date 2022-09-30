package com.cintap.transport.common.enums;

public enum TRANSACTIONSOURCE {
 	ELECTRONIC(1,"EC"),
	MANUAL(2,"MN"),
	UI(3,"UI"),
	IMPORT(4,"IMPORT"),
	FTP(5,"FTP"),
	SFTP(6,"SFTP"),
	API(7,"API");
	
	private Integer code;
	private String value;
	
	private TRANSACTIONSOURCE(Integer code,String value) {
		this.code=code;
		this.value = value;
	}
	
	public Integer getCode() {
		return this.code;
	}
	public String getValue() {
		return this.value;
	}
}
