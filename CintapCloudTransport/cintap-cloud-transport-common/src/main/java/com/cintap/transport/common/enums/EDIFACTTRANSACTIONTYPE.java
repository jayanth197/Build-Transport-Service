package com.cintap.transport.common.enums;

public enum EDIFACTTRANSACTIONTYPE {
	DESADV_ASN(1,"DESADV"),
	ORDERS(2,"ORDERS"),
	APERAK(3,"APERAK"),
	IFTMAN(4,"IFTMAN"),
	IFTSTA(5,"IFTSTA"),
	SHIP_NOTICE(6,"SHIP_NOTICE");
	
	private int code;
	private String value;
	
	private EDIFACTTRANSACTIONTYPE(Integer code,String value) {
		this.code = code;
		this.value = value;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public String getValue() {
		return this.value;
	}

}
