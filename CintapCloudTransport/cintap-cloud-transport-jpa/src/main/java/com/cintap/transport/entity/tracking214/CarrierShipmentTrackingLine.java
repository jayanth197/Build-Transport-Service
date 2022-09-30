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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "carrier_shipment_tracking_line")
public class CarrierShipmentTrackingLine implements java.io.Serializable {

	private static final long serialVersionUID = 8724911972239145995L;
	private Integer lineId;
	private CarrierShipmentTrackingHeader carrierShipmentTrackingHeader;
	private String shipStatusSeqId;
	private String shipStatusCode;
	private String shipStatusReasonCode;
	private String shipAppointmentStatusCode;
	private String shipAppointmentReasonCode;
	private String numberOfUnitShipped;
	private String date;
	private String time;
	private String shipLocationCity;
	private String shipLocationState;
	private String shipLocationCountry;
	private String carrierScac;
	private String carrierEquipmentId;
	private List<CarrierShipmentTrackingWeightPackingQuantityInfo> carrierShipmentTrackingWeightPackingQuantityInfos = new ArrayList<>(0);
	private List<CarrierShipmentTrackingLineMessage> carrierShipmentTrackingLineMessages = new ArrayList<>(0);
	private List<CarrierShipmentTrackingLineReference> carrierShipmentTrackingLineReferences = new ArrayList<>(0);
	private List<CarrierShipmentTrackingLadingException> carrierShipmentTrackingLadingException = new ArrayList<>(0);
	
	public void addCarrierShipmentTrackingWeightPackingQuantityInfo(CarrierShipmentTrackingWeightPackingQuantityInfo quantityInfo) {
		if (quantityInfo == null) {
			return;
		}
		quantityInfo.setCarrierShipmentTrackingLine(this);
		if (carrierShipmentTrackingWeightPackingQuantityInfos == null || carrierShipmentTrackingWeightPackingQuantityInfos.isEmpty()) {
			carrierShipmentTrackingWeightPackingQuantityInfos = new ArrayList<>();
			carrierShipmentTrackingWeightPackingQuantityInfos.add(quantityInfo);
		} else if (!carrierShipmentTrackingWeightPackingQuantityInfos.contains(quantityInfo)) {
			carrierShipmentTrackingWeightPackingQuantityInfos.add(quantityInfo);
		}
	}

	public void addCarrierShipmentTrackingLineMessage(CarrierShipmentTrackingLineMessage trackingLineMessage) {
		if (trackingLineMessage == null) {
			return;
		}
		trackingLineMessage.setCarrierShipmentTrackingLine(this);
		if (carrierShipmentTrackingLineMessages == null || carrierShipmentTrackingLineMessages.isEmpty()) {
			carrierShipmentTrackingLineMessages = new ArrayList<>();
			carrierShipmentTrackingLineMessages.add(trackingLineMessage);
		} else if (!carrierShipmentTrackingLineMessages.contains(trackingLineMessage)) {
			carrierShipmentTrackingLineMessages.add(trackingLineMessage);
		}
	}
	public void addCarrierShipmentTrackingLineReference(CarrierShipmentTrackingLineReference trackingLineReference) {
		if (trackingLineReference == null) {
			return;
		}
		trackingLineReference.setCarrierShipmentTrackingLine(this);
		if (carrierShipmentTrackingLineReferences == null || carrierShipmentTrackingLineReferences.isEmpty()) {
			carrierShipmentTrackingLineReferences = new ArrayList<>();
			carrierShipmentTrackingLineReferences.add(trackingLineReference);
		} else if (!carrierShipmentTrackingLineReferences.contains(trackingLineReference)) {
			carrierShipmentTrackingLineReferences.add(trackingLineReference);
		}
	}

	public void addCarrierShipmentTrackingLadingException(CarrierShipmentTrackingLadingException trackingLadingException) {
		if (trackingLadingException == null) {
			return;
		}
		trackingLadingException.setCarrierShipmentTrackingLine(this);
		if (carrierShipmentTrackingLadingException == null || carrierShipmentTrackingLadingException.isEmpty()) {
			carrierShipmentTrackingLadingException = new ArrayList<>();
			carrierShipmentTrackingLadingException.add(trackingLadingException);
		} else if (!carrierShipmentTrackingLadingException.contains(trackingLadingException)) {
			carrierShipmentTrackingLadingException.add(trackingLadingException);
		}
	}
	
	public CarrierShipmentTrackingLine() {
	}

	public CarrierShipmentTrackingLine(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader,
			String shipStatusSeqId, String shipStatusCode, String shipStatusReasonCode,
			String shipAppointmentStatusCode, String shipAppointmentReasonCode, String numberOfUnitShipped, String date,
			String time, String shipLocationCity, String shipLocationState, String shipLocationCountry,
			String carrierScac, String carrierEquipmentId) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
		this.shipStatusSeqId = shipStatusSeqId;
		this.shipStatusCode = shipStatusCode;
		this.shipStatusReasonCode = shipStatusReasonCode;
		this.shipAppointmentStatusCode = shipAppointmentStatusCode;
		this.shipAppointmentReasonCode = shipAppointmentReasonCode;
		this.numberOfUnitShipped = numberOfUnitShipped;
		this.date = date;
		this.time = time;
		this.shipLocationCity = shipLocationCity;
		this.shipLocationState = shipLocationState;
		this.shipLocationCountry = shipLocationCountry;
		this.carrierScac = carrierScac;
		this.carrierEquipmentId = carrierEquipmentId;
	}

	public CarrierShipmentTrackingLine(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader,
			String shipStatusSeqId, String shipStatusCode, String shipStatusReasonCode,
			String shipAppointmentStatusCode, String shipAppointmentReasonCode, String numberOfUnitShipped, String date,
			String time, String shipLocationCity, String shipLocationState, String shipLocationCountry,
			String carrierScac, String carrierEquipmentId,
			List<CarrierShipmentTrackingLadingException> carrierShipmentTrackingLadingException,
			List<CarrierShipmentTrackingWeightPackingQuantityInfo> carrierShipmentTrackingWeightPackingQuantityInfos,
			List<CarrierShipmentTrackingLineMessage> carrierShipmentTrackingLineMessages,
			List<CarrierShipmentTrackingLineReference> carrierShipmentTrackingLineReferences) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
		this.shipStatusSeqId = shipStatusSeqId;
		this.shipStatusCode = shipStatusCode;
		this.shipStatusReasonCode = shipStatusReasonCode;
		this.shipAppointmentStatusCode = shipAppointmentStatusCode;
		this.shipAppointmentReasonCode = shipAppointmentReasonCode;
		this.numberOfUnitShipped = numberOfUnitShipped;
		this.date = date;
		this.time = time;
		this.shipLocationCity = shipLocationCity;
		this.shipLocationState = shipLocationState;
		this.shipLocationCountry = shipLocationCountry;
		this.carrierScac = carrierScac;
		this.carrierEquipmentId = carrierEquipmentId;
		this.carrierShipmentTrackingWeightPackingQuantityInfos = carrierShipmentTrackingWeightPackingQuantityInfos;
		this.carrierShipmentTrackingLineMessages = carrierShipmentTrackingLineMessages;
		this.carrierShipmentTrackingLineReferences = carrierShipmentTrackingLineReferences;
		this.carrierShipmentTrackingLadingException = carrierShipmentTrackingLadingException;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "line_id", unique = true, nullable = false)
	public Integer getLineId() {
		return this.lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnoreProperties("carrierShipmentTrackingLines")
	public CarrierShipmentTrackingHeader getCarrierShipmentTrackingHeader() {
		return this.carrierShipmentTrackingHeader;
	}

	public void setCarrierShipmentTrackingHeader(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {
		this.carrierShipmentTrackingHeader = carrierShipmentTrackingHeader;
	}

	@Column(name = "ship_status_seq_id",  length = 50)
	public String getShipStatusSeqId() {
		return this.shipStatusSeqId;
	}

	public void setShipStatusSeqId(String shipStatusSeqId) {
		this.shipStatusSeqId = shipStatusSeqId;
	}

	@Column(name = "ship_status_code",  length = 50)
	public String getShipStatusCode() {
		return this.shipStatusCode;
	}

	public void setShipStatusCode(String shipStatusCode) {
		this.shipStatusCode = shipStatusCode;
	}

	@Column(name = "ship_status_reason_code",  length = 50)
	public String getShipStatusReasonCode() {
		return this.shipStatusReasonCode;
	}

	public void setShipStatusReasonCode(String shipStatusReasonCode) {
		this.shipStatusReasonCode = shipStatusReasonCode;
	}

	@Column(name = "ship_appointment_status_code",  length = 50)
	public String getShipAppointmentStatusCode() {
		return this.shipAppointmentStatusCode;
	}

	public void setShipAppointmentStatusCode(String shipAppointmentStatusCode) {
		this.shipAppointmentStatusCode = shipAppointmentStatusCode;
	}

	@Column(name = "ship_appointment_reason_code",  length = 50)
	public String getShipAppointmentReasonCode() {
		return this.shipAppointmentReasonCode;
	}

	public void setShipAppointmentReasonCode(String shipAppointmentReasonCode) {
		this.shipAppointmentReasonCode = shipAppointmentReasonCode;
	}

	@Column(name = "number_of_unit_shipped",  length = 50)
	public String getNumberOfUnitShipped() {
		return this.numberOfUnitShipped;
	}

	public void setNumberOfUnitShipped(String numberOfUnitShipped) {
		this.numberOfUnitShipped = numberOfUnitShipped;
	}

	@Column(name = "date",  length = 50)
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "time",  length = 50)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "ship_location_city",  length = 250)
	public String getShipLocationCity() {
		return this.shipLocationCity;
	}

	public void setShipLocationCity(String shipLocationCity) {
		this.shipLocationCity = shipLocationCity;
	}

	@Column(name = "ship_location_state",  length = 250)
	public String getShipLocationState() {
		return this.shipLocationState;
	}

	public void setShipLocationState(String shipLocationState) {
		this.shipLocationState = shipLocationState;
	}

	@Column(name = "ship_location_country", length = 250)
	public String getShipLocationCountry() {
		return this.shipLocationCountry;
	}

	public void setShipLocationCountry(String shipLocationCountry) {
		this.shipLocationCountry = shipLocationCountry;
	}

	@Column(name = "carrier_scac",  length = 6)
	public String getCarrierScac() {
		return this.carrierScac;
	}

	public void setCarrierScac(String carrierScac) {
		this.carrierScac = carrierScac;
	}

	@Column(name = "carrier_equipment_id",  length = 250)
	public String getCarrierEquipmentId() {
		return this.carrierEquipmentId;
	}

	public void setCarrierEquipmentId(String carrierEquipmentId) {
		this.carrierEquipmentId = carrierEquipmentId;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingLine")
	@JsonIgnoreProperties("carrierShipmentTrackingLine")
	public List<CarrierShipmentTrackingWeightPackingQuantityInfo> getCarrierShipmentTrackingWeightPackingQuantityInfos() {
		return this.carrierShipmentTrackingWeightPackingQuantityInfos;
	}

	public void setCarrierShipmentTrackingWeightPackingQuantityInfos(
			List<CarrierShipmentTrackingWeightPackingQuantityInfo> carrierShipmentTrackingWeightPackingQuantityInfos) {
		this.carrierShipmentTrackingWeightPackingQuantityInfos = carrierShipmentTrackingWeightPackingQuantityInfos;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingLine")
	@JsonIgnoreProperties("carrierShipmentTrackingLine")
	public List<CarrierShipmentTrackingLineMessage> getCarrierShipmentTrackingLineMessages() {
		return this.carrierShipmentTrackingLineMessages;
	}

	public void setCarrierShipmentTrackingLineMessages(
			List<CarrierShipmentTrackingLineMessage> carrierShipmentTrackingLineMessages) {
		this.carrierShipmentTrackingLineMessages = carrierShipmentTrackingLineMessages;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingLine")
	@JsonIgnoreProperties("carrierShipmentTrackingLine")
	public List<CarrierShipmentTrackingLineReference> getCarrierShipmentTrackingLineReferences() {
		return this.carrierShipmentTrackingLineReferences;
	}

	public void setCarrierShipmentTrackingLineReferences(
			List<CarrierShipmentTrackingLineReference> carrierShipmentTrackingLineReferences) {
		this.carrierShipmentTrackingLineReferences = carrierShipmentTrackingLineReferences;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingLine")
	@JsonIgnoreProperties("carrierShipmentTrackingLine")
	public List<CarrierShipmentTrackingLadingException> getCarrierShipmentTrackingLadingException() {
		return this.carrierShipmentTrackingLadingException;
	}

	public void setCarrierShipmentTrackingLadingException(List<CarrierShipmentTrackingLadingException> carrierShipmentTrackingLadingExceptions) {
		this.carrierShipmentTrackingLadingException = carrierShipmentTrackingLadingExceptions;
	}
}
