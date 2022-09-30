package com.cintap.transport.common.enums;

public enum LOGTYPE {
	ERROR("ERROR"),
	INFO("INFO"),
	WARNING("WARNING");
	private String value;
	private LOGTYPE(String value) {
		this.value=value;
	}
	public String getValue() {
		return this.value;
	}
}
