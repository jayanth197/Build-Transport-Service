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

@Table(name="bpi_log_detail")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BpiLogDetail implements Serializable{
	private static final long serialVersionUID = 8189095688584054462L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="log_detail_id")
	private Integer logDetailId;
	
	@Column(name="log_hdr_id")
	private Integer logHeaderId;
	
	@Column(name="process_id")
	private Integer processId;
	
	@Column(name="log_type")
	private String logType;
	
	@Column(name="log_details")
	private String logDetails;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
}
