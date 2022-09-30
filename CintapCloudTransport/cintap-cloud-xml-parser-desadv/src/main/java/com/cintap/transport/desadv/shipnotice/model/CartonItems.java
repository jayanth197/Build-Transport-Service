package com.cintap.transport.desadv.shipnotice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CartonItems")
public class CartonItems { 
	private CartonItem CartonItem;

	@XmlElement(name="CartonItem")
	public CartonItem getCartonItem() {
		return CartonItem;
	}

	public void setCartonItem(CartonItem cartonItem) {
		CartonItem = cartonItem;
	}
	
	
}

