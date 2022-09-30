package com.cintap.transport.iftman.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Header")
public class Header { 
	private String TenantID;
	private String SenderCode;
	private String ReceiverCode;
	private String DocumentCreation;
	private String MessageReference;
	
	@XmlElement(name = "TenantID")
	public String getTenantID() {
		return TenantID;
	}
	public void setTenantID(String tenantID) {
		TenantID = tenantID;
	}
	
	@XmlElement(name = "SenderCode")
	public String getSenderCode() {
		return SenderCode;
	}
	public void setSenderCode(String senderCode) {
		SenderCode = senderCode;
	}
	
	@XmlElement(name = "ReceiverCode")
	public String getReceiverCode() {
		return ReceiverCode;
	}
	public void setReceiverCode(String receiverCode) {
		ReceiverCode = receiverCode;
	}
	
	@XmlElement(name = "DocumentCreation")
	public String getDocumentCreation() {
		return DocumentCreation;
	}
	public void setDocumentCreation(String documentCreation) {
		DocumentCreation = documentCreation;
	}
	
	@XmlElement(name = "MessageReference")
	public String getMessageReference() {
		return MessageReference;
	}
	public void setMessageReference(String messageReference) {
		MessageReference = messageReference;
	}
	
}

