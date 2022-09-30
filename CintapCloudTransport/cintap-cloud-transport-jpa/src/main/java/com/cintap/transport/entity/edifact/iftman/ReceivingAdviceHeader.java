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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="edifact_receiving_advice_header")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingAdviceHeader implements Serializable{

	private static final long serialVersionUID = -6949329890843828251L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="bpi_log_id")
	private Integer bpiLogId;

	@Column(name="tenant_id")
	private String tenantId;

	@Column(name="sender_code")
	private String senderCode;

	@Column(name="receiver_code")
	private String receiverCode;

	@Column(name="document_creation_date")
	private String documentCreationDate;

	@Column(name="message_reference")
	private String messageReference;

	@Column(name = "identification")
	private String identification;
	
	@Column(name="document_name")
	private String documentName;

	@Column(name="created_date")
	private String createdDate;

	@Column(name="updated_date")
	private String updatedDate;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receivingAdviceHeader", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("receivingAdviceHeader")
	private List<ReceivingAdviceReceipt> lstReceivingAdviceReceipt;
	
	public void addReceivingAdviceReceipt(ReceivingAdviceReceipt receivingAdviceReceipt) {
		if (receivingAdviceReceipt == null) {
			return;
		}
		receivingAdviceReceipt.setReceivingAdviceHeader(this);
		if (lstReceivingAdviceReceipt == null || lstReceivingAdviceReceipt.isEmpty()) {
			lstReceivingAdviceReceipt = new ArrayList<>();
			lstReceivingAdviceReceipt.add(receivingAdviceReceipt);
		} else if (!lstReceivingAdviceReceipt.contains(receivingAdviceReceipt)) {
			lstReceivingAdviceReceipt.add(receivingAdviceReceipt);
		}
	}

}