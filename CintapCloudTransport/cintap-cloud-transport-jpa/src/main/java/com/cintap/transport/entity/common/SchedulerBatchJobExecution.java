/**
 * 
 */
package com.cintap.transport.entity.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author SurenderMogiloju
 *
 */
public class SchedulerBatchJobExecution implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="scheduler_execution_id")
	private String schedulerExecutionId;

	@Column(name="pf_automation_execution_id")
	private String pfAutomationExecutionId;

	@Column(name="pf_orchestration_execution_id")
	private String pfOrchestrationExecutionId;

	@Column(name="pf_automation_id")
	private String pfAutomationId;

	@Column(name="process_flow_id")
	private String processFlowId;

	@Column(name="pf_orchestration_id")
	private String pfOrchestrationId;

	@Column(name="process_id")
	private String processId;

	@Column(name="batch_status")
	private String batchStatus;

	@Column(name="start_time")
	private Timestamp startTime;

	@Column(name="end_time")
	private Timestamp endTime;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Date createdDate;
		
	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Date updatedDate;

	

}
