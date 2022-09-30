package com.cintap.transport.common.enums;

public enum PARSERTYPE {
	STANDARD("STANDARD"),
	CUSTOM("CUSTOM");
	
	private String value;
	
	private PARSERTYPE(String type) {
		this.value = type;
	}
	
	public String getValue() {
		return value;
	}
}
