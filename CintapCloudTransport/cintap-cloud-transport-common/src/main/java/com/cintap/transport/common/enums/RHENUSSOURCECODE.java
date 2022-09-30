package com.cintap.transport.common.enums;

import java.util.Arrays;
import java.util.Optional;

public enum RHENUSSOURCECODE {
	CSAFL("728", "CSAFL"),
	CBZFL("4928", "CBZFL"),
	BSAFL("2323", "BSAFL"),
	BBZFL("4927", "BBZFL");

	private String code;
	private String value;

	private RHENUSSOURCECODE(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public static Optional<RHENUSSOURCECODE> getSourceByValue(String value) {
		return Arrays.stream(RHENUSSOURCECODE.values()).filter(source -> source.value.equals(value)).findFirst();
	}

	public static Optional<RHENUSSOURCECODE> getSourceByCode(String value) {
		return Arrays.stream(RHENUSSOURCECODE.values()).filter(source -> source.code.equals(value)).findFirst();
	}

}
