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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Entity
@AllArgsConstructor
@Table(name = "carrier_shipment_tracking_header", uniqueConstraints = @UniqueConstraint(columnNames = {
		"shipment_number", "sender_partner_id" }))
public class CarrierShipmentTrackingHeader implements java.io.Serializable {
	private static final long serialVersionUID = -3425015397813808146L;
	private Integer id;
	private Integer bpiTransId;
	private String senderPartnerId;
	private String receiverPartnerId;
	private String senderIsaId;
	private String receiverIsaId;
	private String isaControlId;
	private String stControlNumber;
	private String shipmentNumber;
	private String shipmentDate;
	private String scacCode;
	private String poNumber;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private int status;
	private List<CarrierShipmentTrackingHeaderMessage> carrierShipmentTrackingHeaderMessages = new ArrayList<>(0);
	private List<CarrierShipmentTrackingHeaderReference> carrierShipmentTrackingHeaderReferences = new ArrayList<>(0);
	private List<CarrierShipmentTrackingAddress> carrierShipmentTrackingAddresses = new ArrayList<>(0);
	private List<CarrierShipmentTrackingLine> carrierShipmentTrackingLines = new ArrayList<>(0);
	private List<CarrierShipmentTrackingSummary> carrierShipmentTrackingSummaries = new ArrayList<>(0);
	private List<CarrierShipmentTrackingAddressInterlineInfo> carrierShipmentTrackingAddressInterlineInfo = new ArrayList<>(0);

	public void addCarrierShipmentTrackingAddressInterlineInfo(CarrierShipmentTrackingAddressInterlineInfo carrierShipmentTrackingAddressInterlineInfoObj) {
		if (carrierShipmentTrackingAddressInterlineInfoObj == null) {
			return;
		}
		carrierShipmentTrackingAddressInterlineInfoObj.setCarrierShipmentTrackingHeader(this);
		if (carrierShipmentTrackingAddressInterlineInfo == null || carrierShipmentTrackingAddressInterlineInfo.isEmpty()) {
			carrierShipmentTrackingAddressInterlineInfo = new ArrayList<>();
			carrierShipmentTrackingAddressInterlineInfo.add(carrierShipmentTrackingAddressInterlineInfoObj);
		} else if (!carrierShipmentTrackingAddressInterlineInfo.contains(carrierShipmentTrackingAddressInterlineInfoObj)) {
			carrierShipmentTrackingAddressInterlineInfo.add(carrierShipmentTrackingAddressInterlineInfoObj);
		}
	}
	
	public void addCarrierShipmentTrackingHeaderMessage(CarrierShipmentTrackingHeaderMessage headerMessage) {
		if (headerMessage == null) {
			return;
		}
		headerMessage.setCarrierShipmentTrackingHeader(this);
		if (carrierShipmentTrackingHeaderMessages == null || carrierShipmentTrackingHeaderMessages.isEmpty()) {
			carrierShipmentTrackingHeaderMessages = new ArrayList<>();
			carrierShipmentTrackingHeaderMessages.add(headerMessage);
		} else if (!carrierShipmentTrackingHeaderMessages.contains(headerMessage)) {
			carrierShipmentTrackingHeaderMessages.add(headerMessage);
		}
	}

	public void addCarrierShipmentTrackingHeaderReference(CarrierShipmentTrackingHeaderReference headerReference) {
		if (headerReference == null) {
			return;
		}
		headerReference.setCarrierShipmentTrackingHeader(this);
		if (carrierShipmentTrackingHeaderReferences == null || carrierShipmentTrackingHeaderReferences.isEmpty()) {
			carrierShipmentTrackingHeaderReferences = new ArrayList<>();
			carrierShipmentTrackingHeaderReferences.add(headerReference);
		} else if (!carrierShipmentTrackingHeaderReferences.contains(headerReference)) {
			carrierShipmentTrackingHeaderReferences.add(headerReference);
		}
	}

	public void addCarrierShipmentTrackingAddresse(CarrierShipmentTrackingAddress trackingAddress) {
		if (trackingAddress == null) {
			return;
		}
		trackingAddress.setCarrierShipmentTrackingHeader(this);
		if (carrierShipmentTrackingAddresses == null || carrierShipmentTrackingAddresses.isEmpty()) {
			carrierShipmentTrackingAddresses = new ArrayList<>();
			carrierShipmentTrackingAddresses.add(trackingAddress);
		} else if (!carrierShipmentTrackingAddresses.contains(trackingAddress)) {
			carrierShipmentTrackingAddresses.add(trackingAddress);
		}
	}

	public void addCarrierShipmentTrackingLine(CarrierShipmentTrackingLine trackingLine) {
		if (trackingLine == null) {
			return;
		}
		trackingLine.setCarrierShipmentTrackingHeader(this);
		if (carrierShipmentTrackingLines == null || carrierShipmentTrackingLines.isEmpty()) {
			carrierShipmentTrackingLines = new ArrayList<>();
			carrierShipmentTrackingLines.add(trackingLine);
		} else if (!carrierShipmentTrackingLines.contains(trackingLine)) {
			carrierShipmentTrackingLines.add(trackingLine);
		}
	}

	public void addCarrierShipmentTrackingSummary(CarrierShipmentTrackingSummary trackingSummary) {
		if (trackingSummary == null) {
			return;
		}
		trackingSummary.setCarrierShipmentTrackingHeader(this);
		if (carrierShipmentTrackingSummaries == null || carrierShipmentTrackingSummaries.isEmpty()) {
			carrierShipmentTrackingSummaries = new ArrayList<>();
			carrierShipmentTrackingSummaries.add(trackingSummary);
		} else if (!carrierShipmentTrackingSummaries.contains(trackingSummary)) {
			carrierShipmentTrackingSummaries.add(trackingSummary);
		}
	}

	public CarrierShipmentTrackingHeader() {
	}

	public CarrierShipmentTrackingHeader(String shipmentNumber, String shipmentDate, String scacCode, String poNumber,
			String createdBy, String createdDate, String updatedBy, String updatedDate, int status) {
		this.shipmentNumber = shipmentNumber;
		this.shipmentDate = shipmentDate;
		this.scacCode = scacCode;
		this.poNumber = poNumber;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.status = status;
	}

	public CarrierShipmentTrackingHeader(Integer bpiTransId, String senderPartnerId, String receiverPartnerId,
			String senderIsaId, String receiverIsaId, String isaControlId, String stControlNumber,
			String shipmentNumber, String shipmentDate, String scacCode, String poNumber, String createdBy,
			String createdDate, String updatedBy, String updatedDate, int status,
			List<CarrierShipmentTrackingHeaderMessage> carrierShipmentTrackingHeaderMessages,
			List<CarrierShipmentTrackingHeaderReference> carrierShipmentTrackingHeaderReferences,
			List<CarrierShipmentTrackingAddress> carrierShipmentTrackingAddresses,
			List<CarrierShipmentTrackingLine> carrierShipmentTrackingLines,
			List<CarrierShipmentTrackingSummary> carrierShipmentTrackingSummaries,
			 List<CarrierShipmentTrackingAddressInterlineInfo> carrierShipmentTrackingAddressInterlineInfo) {
		this.bpiTransId = bpiTransId;
		this.senderPartnerId = senderPartnerId;
		this.receiverPartnerId = receiverPartnerId;
		this.senderIsaId = senderIsaId;
		this.receiverIsaId = receiverIsaId;
		this.isaControlId = isaControlId;
		this.stControlNumber = stControlNumber;
		this.shipmentNumber = shipmentNumber;
		this.shipmentDate = shipmentDate;
		this.scacCode = scacCode;
		this.poNumber = poNumber;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.status = status;
		this.carrierShipmentTrackingHeaderMessages = carrierShipmentTrackingHeaderMessages;
		this.carrierShipmentTrackingHeaderReferences = carrierShipmentTrackingHeaderReferences;
		this.carrierShipmentTrackingAddresses = carrierShipmentTrackingAddresses;
		this.carrierShipmentTrackingLines = carrierShipmentTrackingLines;
		this.carrierShipmentTrackingSummaries = carrierShipmentTrackingSummaries;
		this.carrierShipmentTrackingAddressInterlineInfo = carrierShipmentTrackingAddressInterlineInfo;
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

	@Column(name = "bpi_log_id")
	public Integer getBpiTransId() {
		return this.bpiTransId;
	}

	public void setBpiTransId(Integer bpiTransId) {
		this.bpiTransId = bpiTransId;
	}

	@Column(name = "sender_partner_id", length = 50)
	public String getSenderPartnerId() {
		return this.senderPartnerId;
	}

	public void setSenderPartnerId(String senderPartnerId) {
		this.senderPartnerId = senderPartnerId;
	}

	@Column(name = "receiver_partner_id", length = 50)
	public String getReceiverPartnerId() {
		return this.receiverPartnerId;
	}

	public void setReceiverPartnerId(String receiverPartnerId) {
		this.receiverPartnerId = receiverPartnerId;
	}

	@Column(name = "sender_isa_id", length = 50)
	public String getSenderIsaId() {
		return this.senderIsaId;
	}

	public void setSenderIsaId(String senderIsaId) {
		this.senderIsaId = senderIsaId;
	}

	@Column(name = "receiver_isa_id", length = 50)
	public String getReceiverIsaId() {
		return this.receiverIsaId;
	}

	public void setReceiverIsaId(String receiverIsaId) {
		this.receiverIsaId = receiverIsaId;
	}

	@Column(name = "isa_control_id", length = 50)
	public String getIsaControlId() {
		return this.isaControlId;
	}

	public void setIsaControlId(String isaControlId) {
		this.isaControlId = isaControlId;
	}

	@Column(name = "st_control_number", length = 50)
	public String getStControlNumber() {
		return this.stControlNumber;
	}

	public void setStControlNumber(String stControlNumber) {
		this.stControlNumber = stControlNumber;
	}

	@Column(name = "shipment_number", nullable = false, length = 50)
	public String getShipmentNumber() {
		return this.shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	@Column(name = "shipment_date",  length = 50)
	public String getShipmentDate() {
		return this.shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	@Column(name = "scac_code",  length = 50)
	public String getScacCode() {
		return this.scacCode;
	}

	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}

	@Column(name = "po_number", length = 50)
	public String getPoNumber() {
		return this.poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	@Column(name = "created_by",  length = 50)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_date", nullable = false, length = 50)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updated_by", length = 50)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "updated_date",  length = 50)
	public String getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "status")
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "carrierShipmentTrackingHeader")
	@JsonIgnoreProperties("carrierShipmentTrackingHeader")
	public List<CarrierShipmentTrackingHeaderMessage> getCarrierShipmentTrackingHeaderMessages() {
		return this.carrierShipmentTrackingHeaderMessages;
	}

	public void setCarrierShipmentTrackingHeaderMessages(
			List<CarrierShipmentTrackingHeaderMessage> carrierShipmentTrackingHeaderMessages) {
		this.carrierShipmentTrackingHeaderMessages = carrierShipmentTrackingHeaderMessages;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingHeader")
	@JsonIgnoreProperties("carrierShipmentTrackingHeader")
	public List<CarrierShipmentTrackingHeaderReference> getCarrierShipmentTrackingHeaderReferences() {
		return this.carrierShipmentTrackingHeaderReferences;
	}

	public void setCarrierShipmentTrackingHeaderReferences(
			List<CarrierShipmentTrackingHeaderReference> carrierShipmentTrackingHeaderReferences) {
		this.carrierShipmentTrackingHeaderReferences = carrierShipmentTrackingHeaderReferences;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingHeader")
	@JsonIgnoreProperties("carrierShipmentTrackingHeader")
	public List<CarrierShipmentTrackingAddress> getCarrierShipmentTrackingAddresses() {
		return this.carrierShipmentTrackingAddresses;
	}

	public void setCarrierShipmentTrackingAddresses(
			List<CarrierShipmentTrackingAddress> carrierShipmentTrackingAddresses) {
		this.carrierShipmentTrackingAddresses = carrierShipmentTrackingAddresses;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingHeader")
	@JsonIgnoreProperties("carrierShipmentTrackingHeader")
	public List<CarrierShipmentTrackingLine> getCarrierShipmentTrackingLines() {
		return this.carrierShipmentTrackingLines;
	}

	public void setCarrierShipmentTrackingLines(List<CarrierShipmentTrackingLine> carrierShipmentTrackingLines) {
		this.carrierShipmentTrackingLines = carrierShipmentTrackingLines;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingHeader")
	@JsonIgnoreProperties("carrierShipmentTrackingHeader")
	public List<CarrierShipmentTrackingSummary> getCarrierShipmentTrackingSummaries() {
		return this.carrierShipmentTrackingSummaries;
	}

	public void setCarrierShipmentTrackingSummaries(
			List<CarrierShipmentTrackingSummary> carrierShipmentTrackingSummaries) {
		this.carrierShipmentTrackingSummaries = carrierShipmentTrackingSummaries;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrierShipmentTrackingHeader")
	@JsonIgnoreProperties("carrierShipmentTrackingHeader")
	public List<CarrierShipmentTrackingAddressInterlineInfo> getCarrierShipmentTrackingAddressInterlineInfo() {
		return this.carrierShipmentTrackingAddressInterlineInfo;
	}

	public void setCarrierShipmentTrackingAddressInterlineInfo(List<CarrierShipmentTrackingAddressInterlineInfo> carrierShipmentTrackingAddressInterlineInfo) {
		this.carrierShipmentTrackingAddressInterlineInfo = carrierShipmentTrackingAddressInterlineInfo;
	}
}
