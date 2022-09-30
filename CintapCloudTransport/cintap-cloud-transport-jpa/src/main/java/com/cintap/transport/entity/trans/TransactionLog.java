package com.cintap.transport.entity.trans;

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
import lombok.ToString;

@Table(name = "transaction_log")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionLog implements Serializable {
	
	private static final long serialVersionUID = 2908527439618663870L;
	
	@Id
	@Column(name = "bpi_log_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bpiLogId;
	
	@Column(name = "ref_code")
	private String refCode;
	
	@Column(name = "batch_id")
	private Integer batchId;
	
	@Column(name = "standard")
	private String standard;
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "process_type")
	private String processType;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "partner_transaction_id")
	private String stpTransId;
	
	@Column(name = "stp_type_id")
	private String stpTypeId;
	
	@Column(name = "transaction_source_id")
	private String stpSourceId;
	
	@Column(name = "partner_process_date")
	private String partnerProcessDate;
	
	@Column(name = "sender_partner_id")
	private String stpId;
	
	@Column(name = "receiver_partner_id")
	private String rtpId;

	@Column(name = "sender_partner_code")
	private String senderPartnerCode;
	
	@Column(name = "receiver_partner_code")
	private String receiverPartnerCode;
	
	@Column(name = "edi_version")
	private String ediVersion;
	
	@Column(name = "isa_control_id")
	private String isaControlId;
		
	@Column(name = "gs_control_id")
	private String gsControlId;
	
	@Column(name = "transaction_type")
	private String transactionType;
	
	@Column(name = "sender_isa")
	private String senderIsa;
	
	@Column(name = "receiver_isa")
	private String receiverIsa;
	
	@Column(name = "st_control_id")
	private String stControlNumber;
	
	@Column(name = "created_date")
	private String createdDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_date")
	private String updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name = "status_id")
	private Integer statusId;

	@Column(name = "ack_status")
	private Integer ackStatus;
	
	@Column(name = "error_message")
	private String errorMessage;
	
	@Transient
	private Integer inOutId;
	
	@Transient
	private String sentRawFile;
	
	@Transient
	private String ackType;
		
}
