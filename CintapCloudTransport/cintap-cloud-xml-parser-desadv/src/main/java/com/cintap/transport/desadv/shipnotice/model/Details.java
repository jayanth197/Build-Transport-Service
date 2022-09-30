package com.cintap.transport.desadv.shipnotice.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Details")
public class Details { 
	private PrimaryContact PrimaryContact;
	private String EnteredTime;
	private String PickedTime;
	private String PackagedTime;
	private String ForwardedTime;
	private String ExportedTime;
	private String Subtotal;
	private String Notes;
	private String CountryOfUltimateDestination;
	private List<Weight> Weight;
	private List<Reference> Reference;

	@XmlElement(name="PrimaryContact")
	public PrimaryContact getPrimaryContact() {
		return PrimaryContact;
	}
	public void setPrimaryContact(PrimaryContact primaryContact) {
		PrimaryContact = primaryContact;
	}
	
	@XmlElement(name="EnteredTime")
	public String getEnteredTime() {
		return EnteredTime;
	}
	public void setEnteredTime(String enteredTime) {
		EnteredTime = enteredTime;
	}
	
	@XmlElement(name="PickedTime")
	public String getPickedTime() {
		return PickedTime;
	}
	public void setPickedTime(String pickedTime) {
		PickedTime = pickedTime;
	}
	
	@XmlElement(name="PackagedTime")
	public String getPackagedTime() {
		return PackagedTime;
	}
	public void setPackagedTime(String packagedTime) {
		PackagedTime = packagedTime;
	}
	
	@XmlElement(name="ForwardedTime")
	public String getForwardedTime() {
		return ForwardedTime;
	}
	public void setForwardedTime(String forwardedTime) {
		ForwardedTime = forwardedTime;
	}
	
	@XmlElement(name="ExportedTime")
	public String getExportedTime() {
		return ExportedTime;
	}
	public void setExportedTime(String exportedTime) {
		ExportedTime = exportedTime;
	}
	
	@XmlElement(name="Subtotal")
	public String getSubtotal() {
		return Subtotal;
	}
	public void setSubtotal(String subtotal) {
		Subtotal = subtotal;
	}
	
	@XmlElement(name="Notes")
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	
	@XmlElement(name="CountryOfUltimateDestination")
	public String getCountryOfUltimateDestination() {
		return CountryOfUltimateDestination;
	}
	public void setCountryOfUltimateDestination(String countryOfUltimateDestination) {
		CountryOfUltimateDestination = countryOfUltimateDestination;
	}
	
	@XmlElement(name="Weight")
	public List<Weight> getWeight() {
		return Weight;
	}
	public void setWeight(List<Weight> weight) {
		Weight = weight;
	}
	
	@XmlElement(name="Reference")
	public List<Reference> getReference() {
		return Reference;
	}
	public void setReference(List<Reference> reference) {
		Reference = reference;
	}
}

