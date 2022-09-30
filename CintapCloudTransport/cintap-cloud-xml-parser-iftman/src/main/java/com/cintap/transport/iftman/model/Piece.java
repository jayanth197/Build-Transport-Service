package com.cintap.transport.iftman.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Piece")
public class Piece { 
	private List<Weight> Weight;
	private List<Length> Length;
	private List<Width> Width;
	private List<Height> Height;
	private String Remark;
	private Items Items;
	private String PieceNumber;
	private String PieceType;
	private String PieceSerial;
	private String CartonCount;

	@XmlElement(name = "Weight")
	public List<Weight> getWeight() {
		return Weight;
	}
	public void setWeight(List<Weight> weight) {
		Weight = weight;
	}
	
	@XmlElement(name = "Length")
	public List<Length> getLength() {
		return Length;
	}
	public void setLength(List<Length> length) {
		Length = length;
	}
	
	@XmlElement(name = "Width")
	public List<Width> getWidth() {
		return Width;
	}
	public void setWidth(List<Width> width) {
		Width = width;
	}
	
	@XmlElement(name = "Height")
	public List<Height> getHeight() {
		return Height;
	}
	public void setHeight(List<Height> height) {
		Height = height;
	}
	
	@XmlElement(name = "Remark")
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	
	@XmlElement(name = "Items")
	public Items getItems() {
		return Items;
	}
	public void setItems(Items items) {
		Items = items;
	}
	
	@XmlAttribute(name = "PieceNumber")
	public String getPieceNumber() {
		return PieceNumber;
	}
	public void setPieceNumber(String pieceNumber) {
		PieceNumber = pieceNumber;
	}
	
	@XmlAttribute(name = "PieceType")
	public String getPieceType() {
		return PieceType;
	}
	public void setPieceType(String pieceType) {
		PieceType = pieceType;
	}
	
	@XmlAttribute(name = "PieceSerial")
	public String getPieceSerial() {
		return PieceSerial;
	}
	public void setPieceSerial(String pieceSerial) {
		PieceSerial = pieceSerial;
	}
	
	@XmlAttribute(name = "CartonCount")
	public String getCartonCount() {
		return CartonCount;
	}
	public void setCartonCount(String cartonCount) {
		CartonCount = cartonCount;
	}
}

