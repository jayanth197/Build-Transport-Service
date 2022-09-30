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

@Table(name="batchid_sequence")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchSequenceId implements Serializable{
	private static final long serialVersionUID = -8718325720412448491L;

	@Id
	@Column(name="batch_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer batchId;
	
	@Column(name="file_name")
	private String fileName;
	@Column(name="status")
	private Integer status;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	private String createdDate;
	@Column(name="updated_date")
	private String updatedDate;
	@Column(name="updated_by")
	private String updatedBy;
}
