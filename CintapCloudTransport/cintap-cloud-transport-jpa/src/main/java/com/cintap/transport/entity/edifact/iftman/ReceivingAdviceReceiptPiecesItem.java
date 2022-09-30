package com.cintap.transport.entity.edifact.iftman;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="edifact_receiving_advice_receipt_pieces_item")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingAdviceReceiptPiecesItem implements Serializable{

	private static final long serialVersionUID = -6949329890843828251L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="lin")
	private String lin;

	@Column(name="condition_code")
	private String conditionCode;

	@Column(name="gross_weight")
	private String grossWeight;

	@Column(name="total_quantity")
	private String totalQuantity;

	@Column(name="part_number")
	private String partNumber;
	
	@ManyToOne
	@JoinColumn(name = "pieces_id")
	@JsonIgnoreProperties("lstReceivingAdviceReceiptPiecesItem")
	private ReceivingAdviceReceiptPieces receivingAdviceReceiptPieces;

}
