package com.cintap.transport.common.enums;

public enum EDIPROCESSTYPE {
	INITIATED(1,"INITIATED"),
	INPROGRESS(2,"INPROGRESS"),
	SUCCESS(3,"SUCCESS"),
	FAILED(4,"FAILED");
	
	
	private Integer code;
	private String value;
	
	private EDIPROCESSTYPE(Integer code,String value) {
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
