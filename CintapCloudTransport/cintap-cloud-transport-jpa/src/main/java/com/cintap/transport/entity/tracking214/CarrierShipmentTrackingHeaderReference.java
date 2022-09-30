package com.cintap.transport.entity.tracking214;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "carrier_shipment_tracking_header_reference")
public class CarrierShipmentTrackingHeaderReference implements java.io.Serializable {

	private Integer refId;
	private CarrierShipmentTrackingHeader carrierShipmentTrackingHeader;
	private String refQualifier;
	private String referenceNumber;

	public CarrierShipmentTrackingHeaderReference() {
	}

	public CarrierShipmentTrackingHeaderReference(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
	}

	public CarrierShipmentTrackingHeaderReference(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader,
			String refQualifier, String referenceNumber) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
		this.refQualifier = refQualifier;
		this.referenceNumber = referenceNumber;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ref_id", unique = true, nullable = false)
	public Integer getRefId() {
		return this.refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnoreProperties("carrierShipmentTrackingHeaderReferences")
	public CarrierShipmentTrackingHeader getCarrierShipmentTrackingHeader() {
		return this.carrierShipmentTrackingHeader;
	}

	public void setCarrierShipmentTrackingHeader(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
	}

	@Column(name = "ref_qualifier", length = 50)
	public String getRefQualifier() {
		return this.refQualifier;
	}

	public void setRefQualifier(String refQualifier) {
		this.refQualifier = refQualifier;
	}

	@Column(name = "reference_number", length = 50)
	public String getReferenceNumber() {
		return this.referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

}
