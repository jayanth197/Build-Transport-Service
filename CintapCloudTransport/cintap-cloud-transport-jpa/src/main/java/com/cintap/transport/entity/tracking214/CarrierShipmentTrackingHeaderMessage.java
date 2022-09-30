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
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "carrier_shipment_tracking_header_message")
public class CarrierShipmentTrackingHeaderMessage implements java.io.Serializable {

	private Integer k1Id;
	private CarrierShipmentTrackingHeader carrierShipmentTrackingHeader;
	private String freeFormMessage;
	private String freeFormMessage2;

	public CarrierShipmentTrackingHeaderMessage() {
	}

	public CarrierShipmentTrackingHeaderMessage(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader,
			String freeFormMessage, String freeFormMessage2) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
		this.freeFormMessage = freeFormMessage;
		this.freeFormMessage2 = freeFormMessage2;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "k1_id", unique = true, nullable = false)
	public Integer getK1Id() {
		return this.k1Id;
	}

	public void setK1Id(Integer k1Id) {
		this.k1Id = k1Id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@JsonIgnoreProperties("carrierShipmentTrackingHeaderMessages")
	public CarrierShipmentTrackingHeader getCarrierShipmentTrackingHeader() {
		return this.carrierShipmentTrackingHeader;
	}

	public void setCarrierShipmentTrackingHeader(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
	}

	@Column(name = "free_form_message", length = 50)
	public String getFreeFormMessage() {
		return this.freeFormMessage;
	}

	public void setFreeFormMessage(String freeFormMessage) {
		this.freeFormMessage = freeFormMessage;
	}

	@Column(name = "free_form_message2", length = 50)
	public String getFreeFormMessage2() {
		return this.freeFormMessage2;
	}

	public void setFreeFormMessage2(String freeFormMessage2) {
		this.freeFormMessage2 = freeFormMessage2;
	}

}
