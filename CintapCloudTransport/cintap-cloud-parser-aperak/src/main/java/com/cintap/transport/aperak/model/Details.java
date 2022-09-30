package com.cintap.transport.aperak.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Details")
public class Details implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Reference> referenceList;

	public void setReferenceList(List<Reference> referenceList) {
		this.referenceList = referenceList;
	}

	@XmlElement(name = "Reference")
	public List<Reference> getReferenceList() {
		if (referenceList == null)
			referenceList = new ArrayList<Reference>();
		return referenceList;
	}

}