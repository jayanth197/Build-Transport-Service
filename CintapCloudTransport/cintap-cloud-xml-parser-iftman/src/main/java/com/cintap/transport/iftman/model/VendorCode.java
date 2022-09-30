package com.cintap.transport.iftman.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="VendorCode")
public class VendorCode { 
	private String Name;
	private String text;
	
	@XmlAttribute(name = "Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@XmlValue
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}

