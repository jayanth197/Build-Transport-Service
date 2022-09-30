package com.cintap.transport.iftman.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Receipt")
public class Receipt { 
	private Details Details;
	private String StatedPalletCount;
	private String StatedCartonCount;
	private List<StatedWeight> StatedWeight;
	private StatedValue StatedValue;
	private Pieces Pieces;
	private String Identification;
	private String Status;
	private String InternalId;
	private String Type;
	
	@XmlElement(name = "Details")
	public Details getDetails() {
		return Details;
	}
	public void setDetails(Details details) {
		Details = details;
	}
	
	@XmlElement(name = "StatedPalletCount")
	public String getStatedPalletCount() {
		return StatedPalletCount;
	}
	public void setStatedPalletCount(String statedPalletCount) {
		StatedPalletCount = statedPalletCount;
	}
	
	@XmlElement(name = "StateCartonCount")
	public String getStatedCartonCount() {
		return StatedCartonCount;
	}
	public void setStatedCartonCount(String statedCartonCount) {
		StatedCartonCount = statedCartonCount;
	}
	
	@XmlElement(name = "StatedWeight")
	public List<StatedWeight> getStatedWeight() {
		return StatedWeight;
	}
	public void setStatedWeight(List<StatedWeight> statedWeight) {
		StatedWeight = statedWeight;
	}
	
	@XmlElement(name = "StatedValue")
	public StatedValue getStatedValue() {
		return StatedValue;
	}
	public void setStatedValue(StatedValue statedValue) {
		StatedValue = statedValue;
	}
	
	@XmlElement(name = "Pieces")
	public Pieces getPieces() {
		return Pieces;
	}
	public void setPieces(Pieces pieces) {
		Pieces = pieces;
	}
	
	@XmlAttribute(name = "Identification")
	public String getIdentification() {
		return Identification;
	}
	public void setIdentification(String identification) {
		Identification = identification;
	}
	
	@XmlAttribute(name = "Status")
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	@XmlAttribute(name = "InternalId")
	public String getInternalId() {
		return InternalId;
	}
	public void setInternalId(String internalId) {
		InternalId = internalId;
	}
	
	@XmlAttribute(name = "Type")
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
	
	
}

