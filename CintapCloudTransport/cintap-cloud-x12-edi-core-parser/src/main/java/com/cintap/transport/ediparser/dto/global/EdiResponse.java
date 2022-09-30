package com.cintap.transport.ediparser.dto.global;

import com.cintap.transport.ediparser.constant.STATUS;

public class EdiResponse {

	public EdiBase ediBase;
	public String ediAck;
	public STATUS status;
	public String statusMessage;
	public EdiBase getEdibase() {
		return ediBase;
	}
	public void setEdibase(EdiBase ediBase) {
		this.ediBase = ediBase;
	}
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
	public String getEdiAck() {
		return ediAck;
	}
	public void setEdiAck(String ediAck) {
		this.ediAck = ediAck;
	}
	

}
