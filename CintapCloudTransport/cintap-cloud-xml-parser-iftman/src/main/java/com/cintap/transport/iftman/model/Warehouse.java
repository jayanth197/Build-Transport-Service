package com.cintap.transport.iftman.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Warehouse")
public class Warehouse {
	private String Company;
	private List<String> AddressLine;
	private String City;
	private String State;
	private String PostalCode;
	private String Country;
	private String InternalId;

	@XmlElement(name = "Company")
	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	@XmlElement(name = "AddressLine")
	public List<String> getAddressLine() {
		return AddressLine;
	}

	public void setAddressLine(List<String> addressLine) {
		AddressLine = addressLine;
	}

	@XmlElement(name = "City")
	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	@XmlElement(name = "State")
	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	@XmlElement(name = "PostalCode")
	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}

	@XmlElement(name = "Country")
	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	@XmlAttribute(name = "InternalId")
	public String getInternalId() {
		return InternalId;
	}

	public void setInternalId(String internalId) {
		InternalId = internalId;
	}

}
