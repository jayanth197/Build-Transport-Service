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
//import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EdifactDesadvHeaderMessage generated by hbm2java
 */
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edifact_orders_header_message", catalog = "cintap_transport")
public class OrdersHeaderMessage implements java.io.Serializable {

	private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "message_version", length = 50)
	private String version;
	
	@ManyToOne
	@JoinColumn(name = "edifact_orders_header_id")
	@JsonIgnoreProperties("lstOrdersHeaderMessage")
	private OrdersHeader ordersHeader;
	
	@Column(name = "message_number", length = 50)
	private String messageNumber;
	
	@Column(name = "doc_type", length = 50)
	private String docType;
	
	@Column(name = "message_release", length = 50)
	private String release;
	
	@Column(name = "agency", length = 50)
	private String agency;

}
