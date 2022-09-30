package com.cintap.transport.desadv.shipnotice.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address { 
	private String Company;
	private List<String> AddressLine;
	private String City;
	private String State;
	private String PostalCode;
	private String Country;
	private String InternalId;

}

