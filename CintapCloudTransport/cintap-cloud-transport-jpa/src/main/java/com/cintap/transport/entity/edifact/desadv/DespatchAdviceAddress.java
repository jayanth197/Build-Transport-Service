package com.cintap.transport.entity.edifact.desadv;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EdifactDesadvSummary generated by hbm2java
 */

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(Include.NON_NULL)
@Table(name = "edifact_desadv_address", catalog = "cintap_transport")
public class DespatchAdviceAddress implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@Column(name="id",insertable = false, updatable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="identifier")
	private String identifier;
	
	@Column(name="name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "edifact_desadv_header_id")
	@JsonIgnoreProperties("lstEdifactDesadvAddresses")
	private DespatchAdviceHeader edifactDesadvHeader;
	
}