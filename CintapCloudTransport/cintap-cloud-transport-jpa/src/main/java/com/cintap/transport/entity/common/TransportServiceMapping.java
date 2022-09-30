package com.cintap.transport.entity.common;

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
@Table(name="transport_service_mapping")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransportServiceMapping {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="sender_partner_id")
	private Integer senderPartnerId;
	
	@Column(name="receiver_partner_id")
	private Integer receiverPartnerId;
	
	@Column(name="direction")
	private String direction;
	
	@Column(name="transaction_type")
	private String transactionType;
	
	@Column(name="service_class_name")
	private String serviceClassName;
	
	@Column(name="service_api_endpoint")
	private String serviceApiEndpoint;

	@Column(name="api_shared_secret_key")
	private String apiSharedSecretKey;

	@Column(name="country_code")
	private String countryCode;
	
	@Column(name="status")
	private int Status;
}
