package com.cintap.transport.entity.edifact.orders;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
@Table(name = "edifact_orders_address", catalog = "cintap_transport")
public class OrdersAddress implements java.io.Serializable {

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
	@JoinColumn(name = "edifact_orders_header_id")
	@JsonIgnoreProperties("lstOrdersAddresses")
	private OrdersHeader ordersHeader;
	
	@Column(name="address1")
	private String address1;
	
	@Column(name="address2")
	private String address2;
	
	@Column(name="address3")
	private String address3;
	
	@Column(name="address4")
	private String address4;
	
	@Column(name="address5")
	private String address5;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country_code")
	private String countryCode;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ordersAddress", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("ordersAddress")
	private List<OrdersContactDetails> lstContactDetails;

	public void addOrdersContactDetails(OrdersContactDetails ordersContactDetails) {
		if (ordersContactDetails == null) {
			return;
		}
		ordersContactDetails.setOrdersAddress(this);
		if (lstContactDetails == null || lstContactDetails.isEmpty()) {
			lstContactDetails = new ArrayList<>();
			lstContactDetails.add(ordersContactDetails);
		} else if (!lstContactDetails.contains(ordersContactDetails)) {
			lstContactDetails.add(ordersContactDetails);
		}
	}
	
}
