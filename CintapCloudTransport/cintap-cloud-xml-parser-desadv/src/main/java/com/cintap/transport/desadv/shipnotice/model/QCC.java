package com.cintap.transport.desadv.shipnotice.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="QCC")
public class QCC { 
	private String Person;
	private boolean text;
	
	@XmlAttribute(name="Person")
	public String getPerson() {
		return Person;
	}
	public void setPerson(String person) {
		Person = person;
	}
	
	@XmlValue
	public boolean isText() {
		return text;
	}
	public void setText(boolean text) {
		this.text = text;
	}
	
}

