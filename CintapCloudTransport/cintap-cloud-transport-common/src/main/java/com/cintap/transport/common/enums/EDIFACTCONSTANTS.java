package com.cintap.transport.common.enums;

public enum EDIFACTCONSTANTS {
	CONTENT("content"),
	COMPOSITE("Composite");
	
	private String value;
	
	private EDIFACTCONSTANTS(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

}
