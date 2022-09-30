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
@Table(name = "carrier_shipment_tracking_summary")
public class CarrierShipmentTrackingSummary implements java.io.Serializable {

	private Integer summId;
	private CarrierShipmentTrackingHeader carrierShipmentTrackingHeader;
	private Integer grossWeight;
	private Integer volume;
	private String volumeUom;
	private Integer ladingQuantity;
	private String ladingUom;

	public CarrierShipmentTrackingSummary() {
	}

	public CarrierShipmentTrackingSummary(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader,
			Integer grossWeight, Integer volume, String volumeUom, Integer ladingQuantity, String ladingUom) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
		this.grossWeight = grossWeight;
		this.volume = volume;
		this.volumeUom = volumeUom;
		this.ladingQuantity = ladingQuantity;
		this.ladingUom = ladingUom;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "summ_id", unique = true, nullable = false)
	public Integer getSummId() {
		return this.summId;
	}

	public void setSummId(Integer summId) {
		this.summId = summId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@JsonIgnoreProperties("carrierShipmentTrackingSummaries")
	public CarrierShipmentTrackingHeader getCarrierShipmentTrackingHeader() {
		return this.carrierShipmentTrackingHeader;
	}

	public void setCarrierShipmentTrackingHeader(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
	}

	@Column(name = "gross_weight")
	public Integer getGrossWeight() {
		return this.grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	@Column(name = "volume")
	public Integer getVolume() {
		return this.volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	@Column(name = "volume_uom", length = 50)
	public String getVolumeUom() {
		return this.volumeUom;
	}

	public void setVolumeUom(String volumeUom) {
		this.volumeUom = volumeUom;
	}

	@Column(name = "lading_quantity")
	public Integer getLadingQuantity() {
		return this.ladingQuantity;
	}

	public void setLadingQuantity(Integer ladingQuantity) {
		this.ladingQuantity = ladingQuantity;
	}

	@Column(name = "lading_uom", length = 50)
	public String getLadingUom() {
		return this.ladingUom;
	}

	public void setLadingUom(String ladingUom) {
		this.ladingUom = ladingUom;
	}

}
