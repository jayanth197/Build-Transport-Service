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
@Table(name = "carrier_shipment_tracking_address_interline_info")
public class CarrierShipmentTrackingAddressInterlineInfo implements java.io.Serializable {

	private Integer lineInfoId;
	private CarrierShipmentTrackingHeader carrierShipmentTrackingHeader;
	private Integer transId;
	private String scacCode;
	private String routingSequenceCode;
	private String city;
	private String transportMethodCode;
	private String stateProvinceCode;

	public CarrierShipmentTrackingAddressInterlineInfo() {
	}

	public CarrierShipmentTrackingAddressInterlineInfo(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader,
			Integer transId, String scacCode, String routingSequenceCode, String city, String transportMethodCode,
			String stateProvinceCode) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
		this.transId = transId;
		this.scacCode = scacCode;
		this.routingSequenceCode = routingSequenceCode;
		this.city = city;
		this.transportMethodCode = transportMethodCode;
		this.stateProvinceCode = stateProvinceCode;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "line_info_id", unique = true, nullable = false)
	public Integer getLineInfoId() {
		return this.lineInfoId;
	}

	public void setLineInfoId(Integer lineInfoId) {
		this.lineInfoId = lineInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@JsonIgnoreProperties("carrierShipmentTrackingAddressInterlineInfos")
	public CarrierShipmentTrackingHeader getCarrierShipmentTrackingHeader() {
		return this.carrierShipmentTrackingHeader;
	}

	public void setCarrierShipmentTrackingHeader(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
	}

	@Column(name = "trans_id")
	public Integer getTransId() {
		return this.transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}

	@Column(name = "scac_code", length = 50)
	public String getScacCode() {
		return this.scacCode;
	}

	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}

	@Column(name = "routing_sequence_Code", length = 50)
	public String getRoutingSequenceCode() {
		return this.routingSequenceCode;
	}

	public void setRoutingSequenceCode(String routingSequenceCode) {
		this.routingSequenceCode = routingSequenceCode;
	}

	@Column(name = "city", length = 250)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "transport_method_code", length = 250)
	public String getTransportMethodCode() {
		return this.transportMethodCode;
	}

	public void setTransportMethodCode(String transportMethodCode) {
		this.transportMethodCode = transportMethodCode;
	}

	@Column(name = "state_province_code", length = 250)
	public String getStateProvinceCode() {
		return this.stateProvinceCode;
	}

	public void setStateProvinceCode(String stateProvinceCode) {
		this.stateProvinceCode = stateProvinceCode;
	}

}
