package com.cintap.transport.entity.edifact.orders;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
 * EdifactDesadvFreeText generated by hbm2java
 */
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edifact_orders_free_text", catalog = "cintap_transport")
public class OrdersFreeText implements java.io.Serializable {

	private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "edifact_orders_header_id")
	@JsonIgnoreProperties("lstOrdersFreeTexts")
	private OrdersHeader ordersHeader;
	
	@Column(name = "qualifier", length = 50)
	private String qualifier;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy ="ordersFreeText",fetch = FetchType.LAZY)
	@JsonIgnoreProperties("ordersFreeText")
	private List<OrdersFreeTextDetails> lstOrdersFreeTextDetails;

	public void addOrdersFreeTextDetails(OrdersFreeTextDetails ordersFreeTextDetails) {
		if (ordersFreeTextDetails == null) {
			return;
		}
		ordersFreeTextDetails.setOrdersFreeText(this);
		if (lstOrdersFreeTextDetails == null || lstOrdersFreeTextDetails.isEmpty()) {
			lstOrdersFreeTextDetails = new ArrayList<>();
			lstOrdersFreeTextDetails.add(ordersFreeTextDetails);
		} else if (!lstOrdersFreeTextDetails.contains(ordersFreeTextDetails)) {
			lstOrdersFreeTextDetails.add(ordersFreeTextDetails);
		}
	}

}
