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

@Table(name="audit_log")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog implements Serializable{


	private static final long serialVersionUID = 6802960275302676183L;

	@Id
	@Column(name="audit_log_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int auditLogId;
	
	@Column(name="tpid")
	private String partnerId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="bpi_log_id")
	private Integer bpiLogId;

	@Column(name="component_name")
	private String componentName;

	@Column(name="component_rec_id")
	private Integer componentRecId;
	
	@Column(name="status")
	private String status;
	
	@Column(name="edi_type")
	private Integer ediType;
	
	@Column(name="action_type")
	private String actionType;
	
	@Column(name="action")
	private String action;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_date")
	private String updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
}
