package com.cintap.transport.aperak.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Acknowledgment")
public class Acknowledgment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String documentAck = "";
	private String identification = "";
	private String referenceNumberAck = "";
	private String responseCode = "";
	private String reason = "";
	private Details details;

	public void setDocumentAck(String documentAck) {
		this.documentAck = documentAck;
	}

	@XmlAttribute(name = "DocumentAck")
	public String getDocumentAck() {
		return documentAck;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@XmlAttribute(name = "Identification")
	public String getIdentification() {
		return identification;
	}

	public void setReferenceNumberAck(String referenceNumberAck) {
		this.referenceNumberAck = referenceNumberAck;
	}

	@XmlAttribute(name = "ReferenceNumberAck")
	public String getReferenceNumberAck() {
		return referenceNumberAck;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@XmlElement(name = "ResponseCode")
	public String getResponseCode() {
		return responseCode;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@XmlElement(name = "Reason")
	public String getReason() {
		return reason;
	}

	@XmlElement(name = "Details")
	public Details getDetails() {
		if (details == null)
			details = new Details();
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

}