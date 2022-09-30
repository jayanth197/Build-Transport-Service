package com.cintap.transport.desadv.shipnotice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PrimaryContact")
public class PrimaryContact { 
	private String Name;

	@XmlElement(name="Name")
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
}

