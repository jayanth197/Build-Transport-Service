package com.cintap.transport.repository.trans;

import java.io.Serializable;
import java.time.LocalDateTime;

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

@Table(name="bpi_batch_job")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchJobLog implements Serializable{
	private static final long serialVersionUID = 6802960275302676183L;

	@Id
	@Column(name="batch_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int batchId;
	
	@Column(name="partner_id")
	private Integer partnerId;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="raw_file")
	private String rawFile;
	
	@Column(name="batch_log")
	private String batchLog;
	
	@Column(name="end_time")
	private String endTime;
	
	@Column(name="no_of_transactions")
	private Integer noOfTransactions;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_date")
	private String updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="batch_status")
	private String batchStatus;
}
