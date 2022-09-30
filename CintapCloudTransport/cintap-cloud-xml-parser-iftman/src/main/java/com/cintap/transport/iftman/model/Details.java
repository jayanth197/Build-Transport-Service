package com.cintap.transport.iftman.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Details")
public class Details { 
	private String CustomerCode;
	private VendorCode VendorCode;
	private String WarehouseCode;
	private String PurchaseOrder;
	private VendorOrderID VendorOrderID;
	private SalesOrder SalesOrder;
	private Warehouse Warehouse;
	private String InboundCarrier;
	private BOL BOL;
	private InvoiceNumber InvoiceNumber;
	private String InvoiceDate;
	private ShipSet ShipSet;
	private String Shipper;
	private String ReceivedTime;
	private String FinalizedTime;
	private String Remark;
	
	@XmlElement(name = "CustomerCode")
	public String getCustomerCode() {
		return CustomerCode;
	}
	public void setCustomerCode(String customerCode) {
		CustomerCode = customerCode;
	}
	
	@XmlElement(name = "VendorCode")
	public VendorCode getVendorCode() {
		return VendorCode;
	}
	public void setVendorCode(VendorCode vendorCode) {
		VendorCode = vendorCode;
	}
	
	@XmlElement(name = "WarehouseCode")
	public String getWarehouseCode() {
		return WarehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		WarehouseCode = warehouseCode;
	}
	
	@XmlElement(name = "PurchaseOrder")
	public String getPurchaseOrder() {
		return PurchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		PurchaseOrder = purchaseOrder;
	}
	
	@XmlElement(name = "VendorOrderID")
	public VendorOrderID getVendorOrderID() {
		return VendorOrderID;
	}
	public void setVendorOrderID(VendorOrderID vendorOrderID) {
		VendorOrderID = vendorOrderID;
	}
	
	@XmlElement(name = "SalesOrder")
	public SalesOrder getSalesOrder() {
		return SalesOrder;
	}
	public void setSalesOrder(SalesOrder salesOrder) {
		SalesOrder = salesOrder;
	}
	
	@XmlElement(name = "Warehouse")
	public Warehouse getWarehouse() {
		return Warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		Warehouse = warehouse;
	}
	
	@XmlElement(name = "InboundCarrier")
	public String getInboundCarrier() {
		return InboundCarrier;
	}
	public void setInboundCarrier(String inboundCarrier) {
		InboundCarrier = inboundCarrier;
	}
	
	@XmlElement(name = "BOL")
	public BOL getBOL() {
		return BOL;
	}
	public void setBOL(BOL bOL) {
		BOL = bOL;
	}
	
	@XmlElement(name = "InvoiceNumber")
	public InvoiceNumber getInvoiceNumber() {
		return InvoiceNumber;
	}
	public void setInvoiceNumber(InvoiceNumber invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}
	
	@XmlElement(name = "InvoiceDate")
	public String getInvoiceDate() {
		return InvoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		InvoiceDate = invoiceDate;
	}
	
	@XmlElement(name = "ShipSet")
	public ShipSet getShipSet() {
		return ShipSet;
	}
	public void setShipSet(ShipSet shipSet) {
		ShipSet = shipSet;
	}
	
	@XmlElement(name = "Shipper")
	public String getShipper() {
		return Shipper;
	}
	public void setShipper(String shipper) {
		Shipper = shipper;
	}
	
	@XmlElement(name = "ReceivedTime")
	public String getReceivedTime() {
		return ReceivedTime;
	}
	public void setReceivedTime(String receivedTime) {
		ReceivedTime = receivedTime;
	}
	
	@XmlElement(name = "FinalizedTime")
	public String getFinalizedTime() {
		return FinalizedTime;
	}
	public void setFinalizedTime(String finalizedTime) {
		FinalizedTime = finalizedTime;
	}
	
	@XmlElement(name = "Remark")
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	
}

