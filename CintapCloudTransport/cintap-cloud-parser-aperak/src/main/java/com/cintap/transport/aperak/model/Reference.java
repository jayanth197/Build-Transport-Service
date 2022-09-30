package com.cintap.transport.aperak.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="Reference")
public class Reference implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key = "";
	private String textContent = "";

	public void setKey(String key) {
		this.key = key;
	}

	@XmlAttribute(name = "key")
	public String getKey() {
		return key;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	@XmlValue
	public String getTextContent() {
		return textContent;
	}

}