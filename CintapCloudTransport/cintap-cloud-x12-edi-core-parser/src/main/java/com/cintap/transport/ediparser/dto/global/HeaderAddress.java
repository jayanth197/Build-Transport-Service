package com.cintap.transport.ediparser.dto.global;

import java.util.List;

import com.cintap.transport.ediparser.dto.segment.x12.N1;
import com.cintap.transport.ediparser.dto.segment.x12.N3;
import com.cintap.transport.ediparser.dto.segment.x12.N4;


public class HeaderAddress {
	
	private N1 n1Segment;
	private List<N3> lstN3Segment;
	private N4 n4Segment;
	public HeaderAddress(N1 n1Segment, List<N3> lstN3Segment, N4 n4Segment) {
		super();
		this.n1Segment = n1Segment;
		this.lstN3Segment = lstN3Segment;
		this.n4Segment = n4Segment;
	}
	public HeaderAddress() {
		// TODO Auto-generated constructor stub
	}
	public N1 getN1Segment() {
		return n1Segment;
	}
	public void setN1Segment(N1 n1Segment) {
		this.n1Segment = n1Segment;
	}
	public List<N3> getLstN3Segment() {
		return lstN3Segment;
	}
	public void setLstN3Segment(List<N3> lstN3Segment) {
		this.lstN3Segment = lstN3Segment;
	}
	public N4 getN4Segment() {
		return n4Segment;
	}
	public void setN4Segment(N4 n4Segment) {
		this.n4Segment = n4Segment;
	}
}
