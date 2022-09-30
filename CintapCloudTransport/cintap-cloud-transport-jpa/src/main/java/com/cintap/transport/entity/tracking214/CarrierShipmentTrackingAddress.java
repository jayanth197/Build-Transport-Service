package com.cintap.transport.entity.tracking214;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;

@Builder
@Entity
@Table(name = "carrier_shipment_tracking_address")
public class CarrierShipmentTrackingAddress implements java.io.Serializable {

	private static final long serialVersionUID = -6667700510534783301L;
	private Integer headerAddId;
	private String city;
	private CarrierShipmentTrackingHeader carrierShipmentTrackingHeader;
	private String addressTypeCode;
	private String addressCode;
	private String duns;
	private String addressLine1;
	private String addressLine2;
	private String state;
	private String country;
	private String zipCode;
	private String contactPersonName;
	private String contactPersonEmail;
	private String contactPersonPhone;
	private String locationCodeQual;
	private String addressLocationNo;
	private String addressAlternateName;
	private String addressName;
	private Boolean isDeleted;
//	private List<CarrierShipmentTrackingAddressInterlineInfo> carrierShipmentTrackingAddressInterlineInfos = new ArrayList<>(0);
//
//	public void addCarrierShipmentTrackingAddressInterlineInfo(CarrierShipmentTrackingAddressInterlineInfo addressLineInfo) {
//		if (addressLineInfo == null) {
//			return;
//		}
//		addressLineInfo.setCarrierShipmentTrackingAddress(this);
//		if (carrierShipmentTrackingAddressInterlineInfos == null || carrierShipmentTrackingAddressInterlineInfos.isEmpty()) {
//			carrierShipmentTrackingAddressInterlineInfos = new ArrayList<>();
//			carrierShipmentTrackingAddressInterlineInfos.add(addressLineInfo);
//		} else if (!carrierShipmentTrackingAddressInterlineInfos.contains(addressLineInfo)) {
//			carrierShipmentTrackingAddressInterlineInfos.add(addressLineInfo);
//		}
//	}
	public CarrierShipmentTrackingAddress() {
	}

	
	public CarrierShipmentTrackingAddress(Integer headerAddId,	String city,
			CarrierShipmentTrackingHeader carrierShipmentTrackingHeader, String addressTypeCode, String addressCode,
			String duns, String addressLine1, String addressLine2, String state, String country, String zipCode,
			String contactPersonName, String contactPersonEmail, String contactPersonPhone, String locationCodeQual,
			String addressLocationNo, String addressAlternateName, String addressName, Boolean isDeleted) {
		this.headerAddId = headerAddId;
		this.city = city;
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
		this.addressTypeCode = addressTypeCode;
		this.addressCode = addressCode;
		this.duns = duns;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
		this.contactPersonName = contactPersonName;
		this.contactPersonEmail = contactPersonEmail;
		this.contactPersonPhone = contactPersonPhone;
		this.locationCodeQual = locationCodeQual;
		this.addressLocationNo = addressLocationNo;
		this.addressAlternateName = addressAlternateName;
		this.addressName = addressName;
		this.isDeleted = isDeleted;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "header_add_id", unique = true, nullable = false)
	public Integer getHeaderAddId() {
		return this.headerAddId;
	}

	public void setHeaderAddId(int headerAddId) {
		this.headerAddId = headerAddId;
	}

	@Column(name = "city", nullable = false)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnoreProperties("carrierShipmentTrackingAddresses")
	public CarrierShipmentTrackingHeader getCarrierShipmentTrackingHeader() {
		return this.carrierShipmentTrackingHeader;
	}

	public void setCarrierShipmentTrackingHeader(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
	}

	@Column(name = "address_type_code", length = 50)
	public String getAddressTypeCode() {
		return this.addressTypeCode;
	}

	public void setAddressTypeCode(String addressTypeCode) {
		this.addressTypeCode = addressTypeCode;
	}

	@Column(name = "address_code", length = 50)
	public String getAddressCode() {
		return this.addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	@Column(name = "duns", length = 50)
	public String getDuns() {
		return this.duns;
	}

	public void setDuns(String duns) {
		this.duns = duns;
	}

	@Column(name = "address_line1")
	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	@Column(name = "address_line2")
	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	@Column(name = "state")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "country")
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "zip_code", length = 10)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "contact_person_name")
	public String getContactPersonName() {
		return this.contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	@Column(name = "contact_person_email")
	public String getContactPersonEmail() {
		return this.contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}

	@Column(name = "contact_person_phone")
	public String getContactPersonPhone() {
		return this.contactPersonPhone;
	}

	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}

	@Column(name = "location_code_qual", length = 50)
	public String getLocationCodeQual() {
		return this.locationCodeQual;
	}

	public void setLocationCodeQual(String locationCodeQual) {
		this.locationCodeQual = locationCodeQual;
	}

	@Column(name = "address_location_no", length = 50)
	public String getAddressLocationNo() {
		return this.addressLocationNo;
	}

	public void setAddressLocationNo(String addressLocationNo) {
		this.addressLocationNo = addressLocationNo;
	}

	@Column(name = "address_alternate_name", length = 50)
	public String getAddressAlternateName() {
		return this.addressAlternateName;
	}

	public void setAddressAlternateName(String addressAlternateName) {
		this.addressAlternateName = addressAlternateName;
	}

	@Column(name = "address_name", length = 50)
	public String getAddressName() {
		return this.addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	@Column(name = "is_deleted")
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

//	@LazyCollection(LazyCollectionOption.FALSE)
//	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingAddress")
//	@JsonIgnoreProperties("carrierShipmentTrackingAddress")
//	public List<CarrierShipmentTrackingAddressInterlineInfo> getCarrierShipmentTrackingAddressInterlineInfos() {
//		return this.carrierShipmentTrackingAddressInterlineInfos;
//	}
//
//	public void setCarrierShipmentTrackingAddressInterlineInfos(List<CarrierShipmentTrackingAddressInterlineInfo> carrierShipmentTrackingAddressInterlineInfos) {
//		this.carrierShipmentTrackingAddressInterlineInfos = carrierShipmentTrackingAddressInterlineInfos;
//	}

}
