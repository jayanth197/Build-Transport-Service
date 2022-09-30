package com.cintap.transport.desadv.shipnotice.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Carton")
public class Carton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QCC QCC;
	private String VendorOrderID;
	private String SalesOrder;
	private String PurchaseOrder;
	private String ShipSet;
	private String VendorCartonID;
	private List<Weight> Weight;
	private InboundTracking InboundTracking;
	private CartonItems CartonItems;
	private String CartonID;
	private String Created;
	private boolean Wood;
	private boolean Lithium;
	private boolean Hazardous;
	private String ConditionCode;
	private String CustomerConditionCode;
//	public String text;

	@XmlElement(name = "QCC")
	public QCC getQCC() {
		return QCC;
	}
	public void setQCC(QCC qCC) {
		QCC = qCC;
	}
	@XmlElement(name = "VendorOrderID")
	public String getVendorOrderID() {
		return VendorOrderID;
	}
	public void setVendorOrderID(String vendorOrderID) {
		VendorOrderID = vendorOrderID;
	}
	@XmlElement(name = "SalesOrder")
	public String getSalesOrder() {
		return SalesOrder;
	}
	public void setSalesOrder(String salesOrder) {
		SalesOrder = salesOrder;
	}
	@XmlElement(name = "PurchaseOrder")
	public String getPurchaseOrder() {
		return PurchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		PurchaseOrder = purchaseOrder;
	}
	@XmlElement(name = "ShipSet")
	public String getShipSet() {
		return ShipSet;
	}
	public void setShipSet(String shipSet) {
		ShipSet = shipSet;
	}
	@XmlElement(name = "VendorCartonID")
	public String getVendorCartonID() {
		return VendorCartonID;
	}
	public void setVendorCartonID(String vendorCartonID) {
		VendorCartonID = vendorCartonID;
	}
	@XmlElement(name = "Weight")
	public List<Weight> getWeight() {
		return Weight;
	}
	public void setWeight(List<Weight> weight) {
		Weight = weight;
	}
	@XmlElement(name = "InboundTracking")
	public InboundTracking getInboundTracking() {
		return InboundTracking;
	}
	public void setInboundTracking(InboundTracking inboundTracking) {
		InboundTracking = inboundTracking;
	}
	@XmlElement(name = "CartonItems")
	public CartonItems getCartonItems() {
		return CartonItems;
	}
	public void setCartonItems(CartonItems cartonItems) {
		CartonItems = cartonItems;
	}
	@XmlAttribute(name = "CartonID")
	public String getCartonID() {
		return CartonID;
	}
	public void setCartonID(String cartonID) {
		CartonID = cartonID;
	}
	@XmlAttribute(name = "Created")
	public String getCreated() {
		return Created;
	}
	public void setCreated(String created) {
		Created = created;
	}
	@XmlAttribute(name = "Wood")
	public boolean isWood() {
		return Wood;
	}
	public void setWood(boolean wood) {
		Wood = wood;
	}
	@XmlAttribute(name = "Lithium")
	public boolean isLithium() {
		return Lithium;
	}
	public void setLithium(boolean lithium) {
		Lithium = lithium;
	}
	@XmlAttribute(name = "Hazardous")
	public boolean isHazardous() {
		return Hazardous;
	}
	public void setHazardous(boolean hazardous) {
		Hazardous = hazardous;
	}
	@XmlAttribute(name = "ConditionCode")
	public String getConditionCode() {
		return ConditionCode;
	}
	public void setConditionCode(String conditionCode) {
		ConditionCode = conditionCode;
	}
	@XmlAttribute(name = "CustomerConditionCode")
	public String getCustomerConditionCode() {
		return CustomerConditionCode;
	}
	public void setCustomerConditionCode(String customerConditionCode) {
		CustomerConditionCode = customerConditionCode;
	}
//	public String getText() {
//		return text;
//	}
//	public void setText(String text) {
//		this.text = text;
//	}
	
	
}

