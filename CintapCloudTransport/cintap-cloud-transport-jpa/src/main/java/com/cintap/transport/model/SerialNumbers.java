package com.cintap.transport.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerialNumbers implements Serializable{
	
	private static final long serialVersionUID = 1609269779636510131L;
	//private String poNumber;
	//private String partNumber;
	private String serialNumber;
	
	public SerialNumbers() {
		
	}
	
	public SerialNumbers(String serialNumber) {
		super();
		//this.poNumber = poNumber;
		//this.partNumber = partNumber;
		this.serialNumber = serialNumber;
	}
	
	
}
