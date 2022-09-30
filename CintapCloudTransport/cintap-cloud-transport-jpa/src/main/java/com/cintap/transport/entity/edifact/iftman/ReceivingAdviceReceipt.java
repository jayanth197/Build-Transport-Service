package com.cintap.transport.entity.edifact.iftman;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="edifact_receiving_advice_receipt")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingAdviceReceipt implements Serializable{

	private static final long serialVersionUID = -6949329890843828251L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="identification")
	private String identification;

	@Column(name="status")
	private String status;

	@Column(name="internal_id")
	private String internalId;

	@Column(name="type")
	private String type;

	@Column(name="stated_pallet_count")
	private String statedPalletCount;

	@Column(name="stated_carton_count")
	private String statedCartonCount;
	
	@Column(name="gross_stated_weight_kg")
	private String grossStatedWeightKg;
	
	@Column(name="gross_stated_weight_lb")
	private String grossStatedWeightLb;
	
	@Column(name="stated_value")
	private String statedValue;
	
	@Column(name="customer_code")
	private String customerCode;
	
	@Column(name="vendor_name")
	private String vendorName;
	
	@Column(name="vendor_code")
	private String vendorCode;
	
	@Column(name="warehouse_code")
	private String warehouseCode;
	
	@Column(name="purchase_order_number")
	private String purchaseOrderNumber;
	
	@Column(name="vendor_order_id")
	private String vendorOrderId;
	
	@Column(name="sales_order")
	private String salesOrder;
	
	@Column(name="inbound_carrier")
	private String inboundCarrier;
	
	@Column(name="bol")
	private String bol;
	
	@Column(name="invoice_number")
	private String invoiceNumber;
	
	@Column(name="invoice_date")
	private String invoiceDate;
	
	@Column(name="ship_set")
	private String shipSet;
	
	@Column(name="shipper")
	private String shipper;
	
	@Column(name="received_time")
	private String receivedTime;

	@Column(name="finalized_time")
	private String finalizedTime;

	@Column(name="remark")
	private String remark;

	@ManyToOne
	@JoinColumn(name = "header_id")
	@JsonIgnoreProperties("lstReceivingAdviceReceipt")
	private ReceivingAdviceHeader receivingAdviceHeader;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receivingAdviceReceipt", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("receivingAdviceReceipt")
	private List<ReceivingAdviceReceiptAddress> lstReceivingAdviceReceiptAddresses;
	
	public void addReceivingAdviceReceiptAddress(ReceivingAdviceReceiptAddress receivingAdviceReceiptAddress) {
		if (receivingAdviceReceiptAddress == null) {
			return;
		}
		receivingAdviceReceiptAddress.setReceivingAdviceReceipt(this);
		if (lstReceivingAdviceReceiptAddresses == null || lstReceivingAdviceReceiptAddresses.isEmpty()) {
			lstReceivingAdviceReceiptAddresses = new ArrayList<>();
			lstReceivingAdviceReceiptAddresses.add(receivingAdviceReceiptAddress);
		} else if (!lstReceivingAdviceReceiptAddresses.contains(receivingAdviceReceiptAddress)) {
			lstReceivingAdviceReceiptAddresses.add(receivingAdviceReceiptAddress);
		}
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receivingAdviceReceipt", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("receivingAdviceReceipt")
	private List<ReceivingAdviceReceiptPieces> lstReceivingAdviceReceiptPieces;
	
	public void addReceivingAdviceReceiptPieces(ReceivingAdviceReceiptPieces receivingAdviceReceiptPieces) {
		if (receivingAdviceReceiptPieces == null) {
			return;
		}
		receivingAdviceReceiptPieces.setReceivingAdviceReceipt(this);
		if (lstReceivingAdviceReceiptPieces == null || lstReceivingAdviceReceiptPieces.isEmpty()) {
			lstReceivingAdviceReceiptPieces = new ArrayList<>();
			lstReceivingAdviceReceiptPieces.add(receivingAdviceReceiptPieces);
		} else if (!lstReceivingAdviceReceiptPieces.contains(receivingAdviceReceiptPieces)) {
			lstReceivingAdviceReceiptPieces.add(receivingAdviceReceiptPieces);
		}
	}

}