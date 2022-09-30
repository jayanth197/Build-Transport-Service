package com.cintap.transport.repository.trans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="edi_version")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EdiVersion implements Serializable{
	private static final long serialVersionUID = 6802960275302676183L;

	@Id
	@Column(name="edi_ver_id")
	@JsonProperty("id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ediVerId;
	
	@Column(name="edi_version")
	private String ediVersion;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_date")
	private String updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="status")
	private Integer status;
}
