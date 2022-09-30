package com.cintap.transport.common.enums;

public enum EDIFILETYPE {
	XML(1,"XML"),
	FLATFILE(2,"FLAT_FILE"),
	EDI(3,"EDI"),
	CXML(4,"CXML"),
	PDF(5,"PDF");
	
	
	private Integer code;
	private String value;
	
	private EDIFILETYPE(Integer code,String value) {
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
