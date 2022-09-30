package com.cintap.transport.desadv.shipnotice.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="Height")
public class Height { 
	private String UnitOfMeasure;
	private String text;
	
	@XmlAttribute(name="UnitOfMeasure")
	public String getUnitOfMeasure() {
		return UnitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		UnitOfMeasure = unitOfMeasure;
	}
	
	@XmlValue
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}

