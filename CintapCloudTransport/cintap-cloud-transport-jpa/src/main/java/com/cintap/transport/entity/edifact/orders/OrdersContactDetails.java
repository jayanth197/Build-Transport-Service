package com.cintap.transport.entity.edifact.orders;

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

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(Include.NON_NULL)
@Table(name = "edifact_orders_contact_details", catalog = "cintap_transport")
public class OrdersContactDetails implements java.io.Serializable {

private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@Column(name="id",insertable = false, updatable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="contact_name")
	private String contactName;
	
	@Column(name="telephone_number")
	private String telephoneNumber;
	
	@Column(name="fax_number")
	private String faxNumber;
	
	@ManyToOne
	@JoinColumn(name = "edifact_orders_address_id")
	@JsonIgnoreProperties("lstContactDetails")
	private OrdersAddress ordersAddress;
	
}
