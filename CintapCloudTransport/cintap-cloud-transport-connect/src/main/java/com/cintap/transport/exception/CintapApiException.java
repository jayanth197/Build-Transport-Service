package com.cintap.transport.exception;

import java.io.Serializable;

import com.cintap.transport.response.CintapBpiResponseCodes;

public class CintapApiException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = -8991119040590101010L;

	private final CintapBpiResponseCodes errorCodes;
	
	private final String message;

	public CintapApiException(CintapBpiResponseCodes errorCodes, String message) {
		super();
		this.errorCodes = errorCodes;
		this.message = message;
	}

	public CintapBpiResponseCodes getErrorCodes() {
		return errorCodes;
	}
	@Override
	public String getMessage() {
		return message;
	}
}
