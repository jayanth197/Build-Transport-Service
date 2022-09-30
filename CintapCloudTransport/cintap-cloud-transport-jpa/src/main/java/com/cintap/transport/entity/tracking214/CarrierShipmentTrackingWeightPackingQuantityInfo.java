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
@Table(name = "carrier_shipment_tracking_weight_packing_quantity_info")
public class CarrierShipmentTrackingWeightPackingQuantityInfo implements java.io.Serializable {
	private static final long serialVersionUID = -741539173257997466L;
	private Integer quantityId;
	private CarrierShipmentTrackingLine carrierShipmentTrackingLine;
	private String weightQualifier;
	private String weightUnitCode;
	private String weight;
	private String ladingQty;
	private String volume;

	public CarrierShipmentTrackingWeightPackingQuantityInfo() {
	}

	public CarrierShipmentTrackingWeightPackingQuantityInfo(CarrierShipmentTrackingLine carrierShipmentTrackingLine,
			String weightQualifier, String weightUnitCode, String weight, String ladingQty, String volume) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
		this.weightQualifier = weightQualifier;
		this.weightUnitCode = weightUnitCode;
		this.weight = weight;
		this.ladingQty = ladingQty;
		this.volume = volume;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "quantity_id", unique = true, nullable = false)
	public Integer getQuantityId() {
		return this.quantityId;
	}

	public void setQuantityId(Integer quantityId) {
		this.quantityId = quantityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	@JsonIgnoreProperties("carrierShipmentTrackingWeightPackingQuantityInfos")
	public CarrierShipmentTrackingLine getCarrierShipmentTrackingLine() {
		return this.carrierShipmentTrackingLine;
	}

	public void setCarrierShipmentTrackingLine(CarrierShipmentTrackingLine carrierShipmentTrackingLine) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
	}

	@Column(name = "weight_qualifier", length = 50)
	public String getWeightQualifier() {
		return this.weightQualifier;
	}

	public void setWeightQualifier(String weightQualifier) {
		this.weightQualifier = weightQualifier;
	}

	@Column(name = "weight_unit_code", length = 50)
	public String getWeightUnitCode() {
		return this.weightUnitCode;
	}

	public void setWeightUnitCode(String weightUnitCode) {
		this.weightUnitCode = weightUnitCode;
	}

	@Column(name = "weight", length = 50)
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Column(name = "lading_qty", length = 50)
	public String getLadingQty() {
		return this.ladingQty;
	}

	public void setLadingQty(String ladingQty) {
		this.ladingQty = ladingQty;
	}

	@Column(name = "volume", length = 50)
	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

}
