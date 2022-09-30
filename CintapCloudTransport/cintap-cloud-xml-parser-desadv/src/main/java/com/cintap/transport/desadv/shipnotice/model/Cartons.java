package com.cintap.transport.desadv.shipnotice.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Cartons")
public class Cartons { 
	private List<Carton> Carton;

	@XmlElement(name="Carton")
	public List<Carton> getCarton() {
		return Carton;
	}

	public void setCarton(List<Carton> carton) {
		Carton = carton;
	}
	
	
}

