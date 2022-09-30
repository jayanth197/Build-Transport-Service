package com.cintap.transport.entity.edifact.desadv.shipnotice;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * EdifactDesadvHeader generated by hbm2java
 */
@Entity
@Table(name = "edifact_desadv_ship_notice_header_reference", catalog = "cintap_transport")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DespatchAdviceShipNoticeHeaderReference implements java.io.Serializable {

	private static final long serialVersionUID = 6802960275302676183L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "ref_key")
	private String key;

	@Column(name = "ref_value")
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "header_id")
	@JsonIgnoreProperties("lstDesadvShipNoitceHeaderReference")
	private DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader;
}