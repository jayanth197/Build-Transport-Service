package com.cintap.transport.entity.trans;

import java.io.Serializable;

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

@Table(name = "transaction_trail")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTrail implements Serializable{
	private static final long serialVersionUID = -3004971766127145500L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="trail_id")
	private int trailId;
	
	@Column(name="bpi_logid")
	private Integer bpiLogId;
	
	@Column(name="master_bpi_log_id")
	private Integer masterBpiLogId;
	
	@Column(name="sender_partner")
	private String senderPartner;
	
	@Column(name="receiver_partner")
	private String receiverPartner;
	
	@Column(name="master_trans_id")
	private String masterTransId;
	
	@Column(name="master_trans_type")
	private String masterTransType;
	
	@Column(name="trail_trans_id")
	private String trailTransId;
	
	@Column(name="trail_trans_type")
	private String trailTransType;
	 
	@Column(name="is_master")
	private int isMaster;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="created_by")
	private String createdBy;

}
