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

@Table(name="edifact_receiving_advice_receipt_address")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingAdviceReceiptAddress implements Serializable{

	private static final long serialVersionUID = -6949329890843828251L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="company")
	private String company;

	@Column(name="address_line1")
	private String addressLine1;

	@Column(name="address_line2")
	private String addressline2;

	@Column(name="city")
	private String city;

	@Column(name="state")
	private String state;

	@Column(name="postal_code")
	private String postalCode;

	@Column(name="country")
	private String country;

	@Column(name="internal_id")
	private String internalId;

	@Column(name="address_type")
	private String addressType;

	@ManyToOne
	@JoinColumn(name = "receipt_id")
	@JsonIgnoreProperties("lstReceivingAdviceReceiptAddress")
	private ReceivingAdviceReceipt receivingAdviceReceipt;

}