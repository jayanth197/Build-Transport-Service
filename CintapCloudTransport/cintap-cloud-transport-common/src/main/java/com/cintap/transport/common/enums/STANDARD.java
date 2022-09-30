package com.cintap.transport.common.enums;

public enum STANDARD {
	EDI("EDI"),
	EDIFACT("EDIFACT"),
	X12("X12"),
	FF("FF");
	
	private String standard;
	
	private STANDARD(String standard) {
		this.standard = standard;
	}
	
	public String getStandard() {
		return standard;
	}
}
