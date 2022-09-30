package com.cintap.transport.entity.edifact.orders;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * EdifactDesadvReference generated by hbm2java
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "edifact_orders_reference", catalog = "cintap_transport")
public class OrdersReference implements java.io.Serializable {

	private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "edifact_orders_header_id")
	@JsonIgnoreProperties("lstOrdersReference")
	private OrdersHeader ordersHeader;
		
	@Column(name = "qualifier", length = 50)
	private String qualifier;
	
	@Column(name = "identifier", length = 50)
	private String identifier;

}