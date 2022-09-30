package com.cintap.transport.common.enums;

import java.util.Arrays;
import java.util.Optional;

public enum APERAKSTATUS {
	AP("AP", "Accepted"),
	RE("RE", "Received"),
	RJ("RJ", "Rejected"),
	ER("RJ", "Error");

	private String code;
	private String value;

	private APERAKSTATUS(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}

	public static Optional<APERAKSTATUS> getStatusByValue(String value) {
		return Arrays.stream(APERAKSTATUS.values()).filter(source -> source.value.equals(value)).findFirst();
	}

	public static Optional<APERAKSTATUS> getStatusByCode(String value) {
		return Arrays.stream(APERAKSTATUS.values()).filter(source -> source.code.equals(value)).findFirst();
	}

}
