package com.cintap.transport.iftman.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
public class Item {
	private String PartNumber;
	private String LIN;
	private String ConditionCode;
	private String GrossWeight;
	private String TotalQty;

	@XmlElement(name = "PartNumber")
	public String getPartNumber() {
		return PartNumber;
	}

	public void setPartNumber(String partNumber) {
		PartNumber = partNumber;
	}

	@XmlAttribute(name = "LIN")
	public String getLIN() {
		return LIN;
	}

	public void setLIN(String lIN) {
		LIN = lIN;
	}

	@XmlAttribute(name = "ConditionCode")
	public String getConditionCode() {
		return ConditionCode;
	}

	public void setConditionCode(String conditionCode) {
		ConditionCode = conditionCode;
	}

	@XmlAttribute(name = "GrossWeight")
	public String getGrossWeight() {
		return GrossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		GrossWeight = grossWeight;
	}

	@XmlAttribute(name = "TotalQty")
	public String getTotalQty() {
		return TotalQty;
	}

	public void setTotalQty(String totalQty) {
		TotalQty = totalQty;
	}
}
