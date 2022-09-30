package com.cintap.transport.entity.edifact.desadv;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EdifactDesadvFreeTextDetails generated by hbm2java
 */
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edifact_desadv_free_text_details", catalog = "cintap_transport")
public class EdifactDesadvFreeTextDetails implements java.io.Serializable {

	private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "edifact_desadv_free_text_id")
	@JsonIgnoreProperties("edifactDesadvFreeTextDetailses")
	private EdifactDesadvFreeText edifactDesadvFreeText;
	
	@Column(name = "sequence", length = 50)
	private String sequence;
	
	@Column(name = "content", length = 50)
	private String content;


}