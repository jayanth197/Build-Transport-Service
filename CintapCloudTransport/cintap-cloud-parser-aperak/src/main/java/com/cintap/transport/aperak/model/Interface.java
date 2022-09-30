package com.cintap.transport.aperak.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Interface")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Interface implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String document = "";
	private String production = "";
	private String schemaVersion = "";
	private String xmlns = "";
	private String xmlns_xsi = "";
	private String xsi_schemaLocation = "";
	private Header header;
	private Acknowledgment acknowledgment;

	public void setDocument(String document) {
		this.document = document;
	}

	@XmlAttribute(name = "Document")
	public String getDocument() {
		return document;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	@XmlAttribute(name = "Production")
	public String getProduction() {
		return production;
	}

	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	@XmlAttribute(name = "SchemaVersion")
	public String getSchemaVersion() {
		return schemaVersion;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	@XmlAttribute(name = "xmlns")
	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns_xsi(String xmlns_xsi) {
		this.xmlns_xsi = xmlns_xsi;
	}

	@XmlAttribute(name = "xmlns:xsi")
	public String getXmlns_xsi() {
		return xmlns_xsi;
	}

	public void setXsi_schemaLocation(String xsi_schemaLocation) {
		this.xsi_schemaLocation = xsi_schemaLocation;
	}

	@XmlAttribute(name = "xsi:schemaLocation")
	public String getXsi_schemaLocation() {
		return xsi_schemaLocation;
	}

	@XmlElement(name = "Header")
	public Header getHeader() {
		if (header == null)
			header = new Header();
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	@XmlElement(name = "Acknowledgment")
	public Acknowledgment getAcknowledgment() {
		if (acknowledgment == null)
			acknowledgment = new Acknowledgment();
		return acknowledgment;
	}

	public void setAcknowledgment(Acknowledgment acknowledgment) {
		this.acknowledgment = acknowledgment;
	}

}