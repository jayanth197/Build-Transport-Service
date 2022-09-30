package com.cintap.transport.desadv.shipnotice.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Interface")
public class Interface implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Header Header;
	private Shipment Shipment;
	private String xmlns = "";
	private String xsi = "";
	private String Document = "";
	private String SchemaVersion = "";
	private String schemaLocation = "";
	private boolean Production;
//	public String text;

	@XmlElement(name = "Header")
	public Header getHeader() {
		return Header;
	}

	public void setHeader(Header header) {
		Header = header;
	}

	@XmlElement(name = "Shipment")
	public Shipment getShipment() {
		return Shipment;
	}

	public void setShipment(Shipment shipment) {
		Shipment = shipment;
	}

	@XmlAttribute(name = "xmlns")
	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	@XmlAttribute(name = "xsi")
	public String getXsi() {
		return xsi;
	}

	public void setXsi(String xsi) {
		this.xsi = xsi;
	}

	@XmlAttribute(name = "Document")
	public String getDocument() {
		return Document;
	}

	public void setDocument(String document) {
		Document = document;
	}

	@XmlAttribute(name = "SchemaVersion")
	public String getSchemaVersion() {
		return SchemaVersion;
	}

	public void setSchemaVersion(String schemaVersion) {
		SchemaVersion = schemaVersion;
	}

	@XmlAttribute(name = "schemaLocation")
	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	@XmlAttribute(name = "Production")
	public boolean isProduction() {
		return Production;
	}

	public void setProduction(boolean production) {
		Production = production;
	}

}
