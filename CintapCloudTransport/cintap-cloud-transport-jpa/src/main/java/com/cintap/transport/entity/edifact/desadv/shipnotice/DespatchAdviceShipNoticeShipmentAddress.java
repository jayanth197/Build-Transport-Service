package com.cintap.transport.entity.edifact.desadv.shipnotice;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cintap.transport.entity.edifact.desadv.DespatchAdviceAddress;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvFreeText;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvHeaderMessage;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvInfo;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvLineItem;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvMessageDateTime;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvReference;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvSummary;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvTransportInformation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
@Table(name = "edifact_desadv_ship_notice_shipment_address", catalog = "cintap_transport")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DespatchAdviceShipNoticeShipmentAddress implements java.io.Serializable {

	private static final long serialVersionUID = 6802960275302676183L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "address_type")
	private String addressType;

	@Column(name = "company")
	private String company;
	
	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;

	@Column(name = "postal_code")
	private String postalCode;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "location_number")
	private String locationNumber;

	@ManyToOne
	@JoinColumn(name = "shipment_id")
	@JsonIgnoreProperties("lstDesadvShipNoitceShipmentAddress")
	private DespatchAdviceShipNoticeShipment despatchAdviceShipNoticeShipment;
}
