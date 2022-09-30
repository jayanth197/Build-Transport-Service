package com.cintap.transport.aperak.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Header")
public class Header implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String documentCreation = "";
	private String messageReference = "";
	private String receiverCode = "";
	private String senderCode = "";
	private String tenantID = "";
	private List<Reference> referenceList;

	public void setDocumentCreation(String documentCreation) {
		this.documentCreation = documentCreation;
	}

	@XmlElement(name = "DocumentCreation")
	public String getDocumentCreation() {
		return documentCreation;
	}

	public void setMessageReference(String messageReference) {
		this.messageReference = messageReference;
	}

	@XmlElement(name = "MessageReference")
	public String getMessageReference() {
		return messageReference;
	}

	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}

	@XmlElement(name = "ReceiverCode")
	public String getReceiverCode() {
		return receiverCode;
	}

	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	@XmlElement(name = "SenderCode")
	public String getSenderCode() {
		return senderCode;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	@XmlElement(name = "TenantID")
	public String getTenantID() {
		return tenantID;
	}

	public void setReferenceList(List<Reference> referenceList) {
		this.referenceList = referenceList;
	}

	@XmlElement(name = "Reference")
	public List<Reference> getReferenceList() {
		if (referenceList == null)
			referenceList = new ArrayList<Reference>();
		return referenceList;
	}

}