package com.cintap.transport.ediparser.dto.global;

import com.cintap.transport.ediparser.constant.STATUS;

public class Response {
	
	public STATUS status;
	public String statusMessage;
	public Object data;
	public String ediAck;
	
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getEdiAck() {
		return ediAck;
	}
	public void setEdiAck(String ediAck) {
		this.ediAck = ediAck;
	}
	

}
