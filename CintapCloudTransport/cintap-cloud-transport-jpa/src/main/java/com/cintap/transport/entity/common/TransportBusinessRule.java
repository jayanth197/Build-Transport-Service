package com.cintap.transport.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="transport_business_rule")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransportBusinessRule {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="sender_partner_id")
	private Integer senderPartnerId;
	
	@Column(name="receiver_partner_id")
	private Integer receiverPartnerId;
	
	@Column(name="asn_validation")
	private Integer asnValidation;
	
	@Column(name="inv_validation")
	private Integer invoiceValidation;
	
	@Column(name="inv_to_po_match")
	private Integer invToPoMatch;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_date")
	private String updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="status")
	private Integer status;
}
