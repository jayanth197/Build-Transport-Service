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
import lombok.ToString;

@Table(name="bpi_log_hdr")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BpiLogHeader implements Serializable{
	private static final long serialVersionUID = -6949329890843828251L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="log_hdr_id")
	private Integer logHdrId;
	
	@Column(name="group_id")
	private Integer groupId;
	
	@Column(name="batch_id")
	private Integer batchId;
	
	@Column(name="bpi_logid")
	private Integer bpiLogId;

	@Column(name="direction")
	private String direction;
	
	@Column(name="type")
	private String type;
	
	@Column(name="process_flow_id")
	private Integer processFlowId;
	
	@Column(name="sender_partner_id")
	private Integer senderPartnerId;
	
	@Column(name="receiver_partner_id")
	private Integer receiverPartnerId;
	
	@Column(name="sender_ptnr_disp_name")
	private String senderPtnrDispName;
	
	@Column(name="receiver_ptnr_disp_name")
	private String receiverPtnrDispName;
	
	@Column(name="file_count")
	private Integer fileCount;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="no_of_transactions")
	private Integer noOfTransactions;
	
	@Column(name="raw_file")
	private String rawFile;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_date")
	private String updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="end_date")
	private String endDate;
	
	@Column(name="batch_status")
	private String batchStatus;
	
}
