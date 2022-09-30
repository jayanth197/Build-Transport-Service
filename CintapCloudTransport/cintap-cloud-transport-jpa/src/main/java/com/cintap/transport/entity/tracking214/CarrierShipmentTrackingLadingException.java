package com.cintap.transport.entity.tracking214;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
@Table(name = "carrier_shipment_tracking_lading_exception")
public class CarrierShipmentTrackingLadingException implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735130277940653472L;
	private Integer ladingId;
	private CarrierShipmentTrackingLine carrierShipmentTrackingLine;
	private String ladingExceptionCode;
	private String packagingFormCode;
	private Integer ladingQuantity;

	public CarrierShipmentTrackingLadingException() {
	}

	public CarrierShipmentTrackingLadingException(CarrierShipmentTrackingLine carrierShipmentTrackingLine,
			String ladingExceptionCode, String packagingFormCode, Integer ladingQuantity) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
		this.ladingExceptionCode = ladingExceptionCode;
		this.packagingFormCode = packagingFormCode;
		this.ladingQuantity = ladingQuantity;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "lading_id", unique = true, nullable = false)
	public Integer getLadingId() {
		return this.ladingId;
	}

	public void setLadingId(Integer ladingId) {
		this.ladingId = ladingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	@JsonIgnoreProperties("carrierShipmentTrackingLine")
	public CarrierShipmentTrackingLine getCarrierShipmentTrackingLine() {
		return this.carrierShipmentTrackingLine;
	}

	public void setCarrierShipmentTrackingLine(CarrierShipmentTrackingLine carrierShipmentTrackingLine) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
	}

	@Column(name = "lading_exception_code", length = 50)
	public String getLadingExceptionCode() {
		return this.ladingExceptionCode;
	}

	public void setLadingExceptionCode(String ladingExceptionCode) {
		this.ladingExceptionCode = ladingExceptionCode;
	}

	@Column(name = "packaging_form_code", length = 50)
	public String getPackagingFormCode() {
		return this.packagingFormCode;
	}

	public void setPackagingFormCode(String packagingFormCode) {
		this.packagingFormCode = packagingFormCode;
	}

	@Column(name = "lading_quantity")
	public Integer getLadingQuantity() {
		return this.ladingQuantity;
	}

	public void setLadingQuantity(Integer ladingQuantity) {
		this.ladingQuantity = ladingQuantity;
	}

}
