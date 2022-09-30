package com.cintap.transport.iftman.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Weight")
public class Weight {
	private String Type;
	private String UnitOfMeasure;
	private String text;

	@XmlAttribute(name = "Type")
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	@XmlAttribute(name = "UnitOfMeasure")
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
