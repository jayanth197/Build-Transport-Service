package com.cintap.transport.entity.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="source_file_types")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourceFileTypes implements Serializable{
	private static final long serialVersionUID = 3279742734416411187L;

	@Id
	@Column(name="source_file_type_id")
	private int sourceFileTypeId;
	
	@Column(name="source_file_type")
	private String sourceFileType;

	public int getSourceFileTypeId() {
		return sourceFileTypeId;
	}

	public void setSourceFileTypeId(int sourceFileTypeId) {
		this.sourceFileTypeId = sourceFileTypeId;
	}

	public String getSourceFileType() {
		return sourceFileType;
	}

	public void setSourceFileType(String sourceFileType) {
		this.sourceFileType = sourceFileType;
	}
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
