package com.cintap.transport.entity.common;

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

@Table(name="otc_import_setup")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImportSetup {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="file_type")
	private String fileType;	
	
	@Column(name="transaction_type")
	private String transactionType;
	
	@Column(name="edi_standard")
	private String ediStandard;	
	
	@Column(name="edi_type")
	private String ediType;	
	
	@Column(name="edi_version")
	private String ediVersion;	
	
	@Column(name="status")
	private int status;	
	
}
