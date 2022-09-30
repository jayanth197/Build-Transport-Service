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
@Table(name="cintap_config")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CintapConfig implements Serializable{

	private static final long serialVersionUID = -1832232276673069236L;
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name="id")
	private int id;
	@Column(name="key")
	private int key;
	@Column(name="value")
	private int value;
	@Column(name="status")
	private Integer status;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	private String createdDate;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_date")
	private String updatedDate;
}


