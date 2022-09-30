package com.cintap.transport.entity.common;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

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
@Table(name = "partner_plants", catalog = "cintap_transport")
public class PartnerPlants implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@Column(name="id",insertable = false, updatable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="partner_id")
	private String partnerId;

	@Column(name="plant_code")
	private String plantCode;
	
	@Column(name="plant_name")
	private String plantName;
}