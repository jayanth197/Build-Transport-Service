package com.cintap.transport.iftman.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Items")
public class Items {
	private Item Item;

	@XmlElement(name = "Item")
	public Item getItem() {
		return Item;
	}

	public void setItem(Item item) {
		Item = item;
	}

}
