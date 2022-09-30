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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twilio.twiml.Text;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="edifact_receiving_advice_receipt_pieces")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingAdviceReceiptPieces implements Serializable{

	private static final long serialVersionUID = -6949329890843828251L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="piece_number")
	private String pieceNumber;

	@Column(name="piece_type")
	private String pieceType;

	@Column(name="piece_serial")
	private String pieceSerial;

	@Column(name="carton_count")
	private String cartonCount;

	@Column(name="gross_weight_kg")
	private String grossWeightKg;

	@Column(name="gross_weight_lb")
	private String grossWeightLb;

	@Column(name="length_inch")
	private String lengthInch;

	@Column(name="length_cm")
	private String lengthCm;

	@Column(name="width_inch")
	private String widthInch;

	@Column(name="width_cm")
	private String widthCm;

	@Column(name="height_inch")
	private String heightInch;

	@Column(name="height_cm")
	private String heightCm;

	@Column(name="remark")
	private String remark;

	@ManyToOne
	@JoinColumn(name = "receipt_id")
	@JsonIgnoreProperties("lstReceivingAdviceReceiptPieces")
	private ReceivingAdviceReceipt receivingAdviceReceipt;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receivingAdviceReceiptPieces", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("receivingAdviceReceiptPieces")
	private List<ReceivingAdviceReceiptPiecesItem> lstReceivingAdviceReceiptPiecesItems;
	
	public void addReceivingAdviceReceiptPiecesItem(ReceivingAdviceReceiptPiecesItem receivingAdviceReceiptPiecesItem) {
		if (receivingAdviceReceiptPiecesItem == null) {
			return;
		}
		receivingAdviceReceiptPiecesItem.setReceivingAdviceReceiptPieces(this);
		if (lstReceivingAdviceReceiptPiecesItems == null || lstReceivingAdviceReceiptPiecesItems.isEmpty()) {
			lstReceivingAdviceReceiptPiecesItems = new ArrayList<>();
			lstReceivingAdviceReceiptPiecesItems.add(receivingAdviceReceiptPiecesItem);
		} else if (!lstReceivingAdviceReceiptPiecesItems.contains(receivingAdviceReceiptPiecesItem)) {
			lstReceivingAdviceReceiptPiecesItems.add(receivingAdviceReceiptPiecesItem);
		}
	}

}
