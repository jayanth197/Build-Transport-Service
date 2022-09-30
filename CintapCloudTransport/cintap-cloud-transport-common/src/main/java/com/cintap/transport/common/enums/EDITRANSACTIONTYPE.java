package com.cintap.transport.common.enums;

public enum EDITRANSACTIONTYPE {
	X_12_EDI_204(1,204),
	X_12_EDI_210(2,210),
	X_12_EDI_214(3,214);
	
	
	private int code;
	private int value;
	
	private EDITRANSACTIONTYPE(Integer code,Integer value) {
		this.code = code;
		this.value = value;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public Integer getValue() {
		return this.value;
	}

}
