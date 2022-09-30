package com.cintap.transport.entity.edifact.desadv;
// Generated May 11, 2022 4:17:20 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@Table(name = "edifact_desadv_free_text", catalog = "cintap_transport")
public class EdifactDesadvFreeText implements java.io.Serializable {

	private static final long serialVersionUID = 6802960275302676183L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "edifact_desadv_header_id")
	@JsonIgnoreProperties("edifactDesadvFreeTexts")
	private DespatchAdviceHeader edifactDesadvHeader;
	
	@Column(name = "qualifier", length = 50)
	private String qualifier;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy ="edifactDesadvFreeText",fetch = FetchType.LAZY)
	@JsonIgnoreProperties("edifactDesadvFreeText")
	private List<EdifactDesadvFreeTextDetails> edifactDesadvFreeTextDetailses;

	public void addEdifactDesadvFreeTextDetails(EdifactDesadvFreeTextDetails edifactDesadvFreeTextDetails) {
		if (edifactDesadvFreeTextDetails == null) {
			return;
		}
		edifactDesadvFreeTextDetails.setEdifactDesadvFreeText(this);
		if (edifactDesadvFreeTextDetailses == null || edifactDesadvFreeTextDetailses.isEmpty()) {
			edifactDesadvFreeTextDetailses = new ArrayList<>();
			edifactDesadvFreeTextDetailses.add(edifactDesadvFreeTextDetails);
		} else if (!edifactDesadvFreeTextDetailses.contains(edifactDesadvFreeTextDetails)) {
			edifactDesadvFreeTextDetailses.add(edifactDesadvFreeTextDetails);
		}
	}

}