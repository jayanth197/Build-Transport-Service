package com.cintap.transport.entity.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="edi_delimiter")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EdiDelimiter implements Serializable{
	private static final long serialVersionUID = -5037641215162955306L;
	@Id
	@Column(name="edi_delimiter_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ediDelimiterId;
	
	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="partner_type")
	private String partnerType;
	
	@Column(name="file_type")
	private String fileType;
	
	@Column(name="sender_partner_id")
	private String senderPartnerId;
	
	@Column(name="sender_isa_id")
	private String senderIsaId;
	
	@Column(name="receiver_partner_id")
	private String receiverPartnerId;
	
	@Column(name="receiver_isa_id")
	private String receiverIsaId;
	
	@Column(name="edi_standard")
	private String ediStandard;
	
	@Column(name="field_delimiter")
	private String fieldDelimiter;

	@Column(name="field_separator")
	private String fieldSeparator;
	
	@Column(name="segment_delimiter")
	private String segmentDelimiter;
	
	@Column(name="usage_indicator")
	private String usageIndicator;
	
	@Column(name="sub_element_separator")
	private String subElementSeparator;
	
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