package com.cintap.transport.common.enums;

public enum DATEFORMAT {
	YYYYMMDDTHHMMSS("yyyy-MM-dd'T'HH:mm:ss"),
	YYYYMMDD("yyyyMMdd"),
	YYYYMMDDHHMMSS("yyyyMMddHHmmss");
	
	private String action;
	
	private DATEFORMAT(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}
}
