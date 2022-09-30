/**
 * 
 */
package com.cintap.transport.entity.common;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

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

/**
 * @author SurenderMogiloju
 *
 */
@Table(name="process_execution_log")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessExecutionLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="bpi_scheduler_batch_job_execution_id")
	private Integer bpiSchedulerBatchJobExecutionId;
	
	@Column(name="process_execution_id")
	private Integer processExecutionId;
	
	@Column(name="file_execution_id")
	private Integer fileExecutionId;
	
	@Column(name="inbound_file_execution_id")
	private Integer inboundFileExecutionId;
	
	@Column(name="partner_id")
	private Integer partnerId;

	@Column(name="process_id")
	private Integer processId;

	@Column(name="sender_partner_id")
	private Integer senderPartnerId;

	@Column(name="receiver_partner_id")
	private Integer receiverPartnerId;

	@Column(name="sender_ptnr_disp_name")
	private String senderPtnrDispName;

	@Column(name="receiver_ptnr_disp_name")
	private String receiverPtnrDispName;
	
	@Transient//@Column(name="sender_isa_id")
	private String senderIsaId;
	
	@Transient//@Column(name="receiver_isa_id")
	private String receiverIsaId;
	
	@Column(name="edi_version")
	private String ediVersion;
	
	@Column(name="trn_type")
	private String trnType;
	
	@Column(name="edi_standard")
	private String ediStandard;
	
	@Column(name="inbound_outbound_ind")
	private String inboundOutboundInd;
	
	@Column(name="is_outboud_generated")
	private String isOutboundGenerated;
	
	@Column(name="edi_type")
	private String ediType;
	
	@Column(name="file_type")
	private String fileType;
	
	@Column(name="source_type")
	private String sourceType;
	
	@Column(name="target_type")
	private String targetType;

	@Column(name="file_count")
	private Integer fileCount;

	@Column(name="file_name")
	private String fileName;

	@Column(name="no_of_transactions")
	private String noOfTransactions;

	@Column(name="raw_file")
	private String rawFile;
	
	@Column(name="status_code")
	private String statusCode;

	@Column(name="batch_status")
	private String batchStatus;

	@Column(name="start_time")
	private Timestamp startTime;

	@Column(name="end_time")
	private Timestamp endTime;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	
}
