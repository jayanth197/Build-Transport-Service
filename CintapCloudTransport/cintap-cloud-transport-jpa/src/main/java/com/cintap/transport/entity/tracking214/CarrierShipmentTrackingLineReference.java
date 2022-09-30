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
@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "carrier_shipment_tracking_line_reference")
public class CarrierShipmentTrackingLineReference implements java.io.Serializable {

	private Integer id;
	private CarrierShipmentTrackingLine carrierShipmentTrackingLine;
	private String refQualifier;
	private String referenceNumber;

	public CarrierShipmentTrackingLineReference() {
	}

	public CarrierShipmentTrackingLineReference(CarrierShipmentTrackingLine carrierShipmentTrackingLine,
			String refQualifier, String referenceNumber) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
		this.refQualifier = refQualifier;
		this.referenceNumber = referenceNumber;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	@JsonIgnoreProperties("carrierShipmentTrackingLineReferences")
	public CarrierShipmentTrackingLine getCarrierShipmentTrackingLine() {
		return this.carrierShipmentTrackingLine;
	}

	public void setCarrierShipmentTrackingLine(CarrierShipmentTrackingLine carrierShipmentTrackingLine) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
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
