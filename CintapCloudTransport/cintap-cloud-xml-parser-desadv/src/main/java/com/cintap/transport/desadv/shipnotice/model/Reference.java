package com.cintap.transport.desadv.shipnotice.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Reference")
public class Reference implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key = "";
	private String text = "";

	@XmlAttribute(name = "key")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@XmlValue
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
