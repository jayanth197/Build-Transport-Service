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



@Table(name="form_template")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormTemplate implements Serializable{

	private static final long serialVersionUID = 591505474323312941L;

	@Id
	@Column(name="template_id")
	private int templateId;

	@Column(name="template_name")
	private String templateName;

	@Column(name="type")
	private String type;

	@Column(name="transaction_type")
	private String transactionType;

	@Column(name="created_date")
	private String createdDate;

	@Column(name="created_by")
	private String createdBy;

}