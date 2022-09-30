package com.cintap.transport.desadv.shipnotice.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Shipment")
public class Shipment { 
	private Details Details;
	private String Shipper;
	private String BillingAddress;
	private String ShippingAddress;
	private String PayToAddress;
	private String UltimateConsignee;
	private String OutboundCarrier;
	private Warehouse Warehouse;
	private String Vendor;
	private Pieces Pieces;
	private String Identification;
	private String Status;
	private String InternalId;
//	public String text;
	
	@XmlElement(name = "Details")
	public Details getDetails() {
		return Details;
	}
	public void setDetails(Details details) {
		Details = details;
	}
	
	@XmlElement(name = "Shipper")
	public String getShipper() {
		return Shipper;
	}
	public void setShipper(String shipper) {
		Shipper = shipper;
	}
	
	@XmlElement(name = "BillingAddress")
	public String getBillingAddress() {
		return BillingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		BillingAddress = billingAddress;
	}
	
	@XmlElement(name = "ShippingAddress")
	public String getShippingAddress() {
		return ShippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		ShippingAddress = shippingAddress;
	}
	
	@XmlElement(name = "PayToAddress")
	public String getPayToAddress() {
		return PayToAddress;
	}
	public void setPayToAddress(String payToAddress) {
		PayToAddress = payToAddress;
	}
	
	@XmlElement(name = "UltimateConsignee")
	public String getUltimateConsignee() {
		return UltimateConsignee;
	}
	public void setUltimateConsignee(String ultimateConsignee) {
		UltimateConsignee = ultimateConsignee;
	}
	
	@XmlElement(name = "OutboundCarrier")
	public String getOutboundCarrier() {
		return OutboundCarrier;
	}
	public void setOutboundCarrier(String outboundCarrier) {
		OutboundCarrier = outboundCarrier;
	}
	
	@XmlElement(name = "Warehouse")
	public Warehouse getWarehouse() {
		return Warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		Warehouse = warehouse;
	}
	
	@XmlElement(name = "Vendor")
	public String getVendor() {
		return Vendor;
	}
	public void setVendor(String vendor) {
		Vendor = vendor;
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
	
}

