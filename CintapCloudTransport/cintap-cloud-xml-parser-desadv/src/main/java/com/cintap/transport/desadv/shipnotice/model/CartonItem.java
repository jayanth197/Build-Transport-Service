package com.cintap.transport.desadv.shipnotice.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CartonItem")
public class CartonItem { 
	private List<String> SerialNumber;
	private String LIN;
	//private String text;
	private String ItemQuantity;
	private String PartNumber;
	private String ScheduleB;
	private String ECCN;
	private UnitPrice UnitPrice;
	private ExtendedPrice ExtendedPrice;
	private String PartDescription;
	private String CustomPartDescription;
	private String CountryOfOrigin;
	private boolean ExportRestricted;
	
	@XmlElement(name = "SerialNumber")
	public List<String> getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(List<String> serialNumber) {
		SerialNumber = serialNumber;
	}
	
	@XmlAttribute(name = "LIN")
	public String getLIN() {
		return LIN;
	}
	public void setLIN(String lIN) {
		LIN = lIN;
	}
	
	@XmlElement(name = "ItemQuantity")
	public String getItemQuantity() {
		return ItemQuantity;
	}
	public void setItemQuantity(String itemQuantity) {
		ItemQuantity = itemQuantity;
	}
	
	@XmlElement(name = "PartNumber")
	public String getPartNumber() {
		return PartNumber;
	}
	public void setPartNumber(String partNumber) {
		PartNumber = partNumber;
	}
	
	@XmlElement(name = "ScheduleB")
	public String getScheduleB() {
		return ScheduleB;
	}
	public void setScheduleB(String scheduleB) {
		ScheduleB = scheduleB;
	}
	
	@XmlElement(name = "ECCN")
	public String getECCN() {
		return ECCN;
	}
	public void setECCN(String eCCN) {
		ECCN = eCCN;
	}
	
	@XmlElement(name = "UnitPrice")
	public UnitPrice getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(UnitPrice unitPrice) {
		UnitPrice = unitPrice;
	}
	
	@XmlElement(name = "ExtendedPrice")
	public ExtendedPrice getExtendedPrice() {
		return ExtendedPrice;
	}
	public void setExtendedPrice(ExtendedPrice extendedPrice) {
		ExtendedPrice = extendedPrice;
	}
	
	@XmlElement(name = "PartDescription")
	public String getPartDescription() {
		return PartDescription;
	}
	public void setPartDescription(String partDescription) {
		PartDescription = partDescription;
	}
	
	@XmlElement(name = "CustomPartDescription")
	public String getCustomPartDescription() {
		return CustomPartDescription;
	}
	public void setCustomPartDescription(String customPartDescription) {
		CustomPartDescription = customPartDescription;
	}
	
	@XmlElement(name = "CountryOfOrigin")
	public String getCountryOfOrigin() {
		return CountryOfOrigin;
	}
	public void setCountryOfOrigin(String countryOfOrigin) {
		CountryOfOrigin = countryOfOrigin;
	}
	
	@XmlElement(name = "ExportRestricted")
	public boolean isExportRestricted() {
		return ExportRestricted;
	}
	public void setExportRestricted(boolean exportRestricted) {
		ExportRestricted = exportRestricted;
	}
}

