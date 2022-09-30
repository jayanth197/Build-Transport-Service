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


@Entity
@Table(name = "carrier_shipment_tracking_line_message")
public class CarrierShipmentTrackingLineMessage implements java.io.Serializable {

	private Integer lineMsgId;
	private CarrierShipmentTrackingLine carrierShipmentTrackingLine;

	public CarrierShipmentTrackingLineMessage() {
	}

	public CarrierShipmentTrackingLineMessage(CarrierShipmentTrackingLine carrierShipmentTrackingLine) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "line_msg_id", unique = true, nullable = false)
	public Integer getLineMsgId() {
		return this.lineMsgId;
	}

	public void setLineMsgId(Integer lineMsgId) {
		this.lineMsgId = lineMsgId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "line_id")
	@JsonIgnoreProperties("carrierShipmentTrackingLineMessages")
	public CarrierShipmentTrackingLine getCarrierShipmentTrackingLine() {
		return this.carrierShipmentTrackingLine;
	}

	public void setCarrierShipmentTrackingLine(CarrierShipmentTrackingLine carrierShipmentTrackingLine) {
		this.carrierShipmentTrackingLine = carrierShipmentTrackingLine;
	}

}
