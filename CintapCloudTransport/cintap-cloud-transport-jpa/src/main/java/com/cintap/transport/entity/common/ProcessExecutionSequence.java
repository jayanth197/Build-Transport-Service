/**
 * 
 */
package com.cintap.transport.entity.common;

import java.io.Serializable;
import java.sql.Timestamp;

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

/**
 * @author SurenderMogiloju
 *
 */
@Table(name="process_execution_sequence")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessExecutionSequence implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pf_orchestration_execution_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer processExecutionId;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_date")
	private Timestamp updatedDate;
}	
