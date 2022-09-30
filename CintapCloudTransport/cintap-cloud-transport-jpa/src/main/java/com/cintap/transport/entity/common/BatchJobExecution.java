package com.cintap.transport.entity.common;

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

@Entity
@Table(name="bpi_batch_job_execution")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchJobExecution implements Serializable{
	private static final long serialVersionUID = 4381118731533651658L;
	@Id
	@Column(name="execution_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int executionId;
	@Column(name="pf_count")
	private int pfCount;
	@Column(name="pf_id")
	private int pfId;
	@Column(name="pf_name")
	private int pfName;
	@Column(name="pf_orchestration_id")
	private int pfOrchestrationId;
	@Column(name="pf_orch_seq_num")
	private int pfOrchSeqNum;
	@Column(name="pf_automation_id")
	private int pfAutomationId;
	@Column(name="partner_id")
	private String partnerId;
	@Column(name="batch_id")
	private int batchId;
	@Column(name="status")
	private String status;
	@Column(name="start_time")
	private String startTime;
	@Column(name="end_time")
	private String endTime;
	@Column(name="description")
	private String description;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="created_by")
	private String createdBy;
}
