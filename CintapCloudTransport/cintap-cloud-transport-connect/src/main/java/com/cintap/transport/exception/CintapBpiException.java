package com.cintap.transport.exception;

public class CintapBpiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String errorCode=null;
	private String errorMessage=null;

	public CintapBpiException(String errorMessage) {
		super(errorMessage);
	}

	public CintapBpiException(final String errorCode,final String errorMessage) {
		this.errorCode = errorCode; 
		this.errorMessage = errorMessage; 
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
