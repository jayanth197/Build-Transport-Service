package com.cintap.transport.exception;

public class InvalidEdiFileException extends RuntimeException{
	private static final long serialVersionUID = -3582096893777563911L;
	private String errorCode=null;
	private String errorMessage=null;

	public InvalidEdiFileException(String errorMessage) {
		super(errorMessage);
	}

	public InvalidEdiFileException(String errorCode,String errorMessage) {
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
