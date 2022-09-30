package com.cintap.transport.ediparser.dto.transaction.x12;

import java.io.Serializable;
import java.util.List;

import com.cintap.transport.ediparser.dto.global.CintapEDIHeader;
import com.cintap.transport.ediparser.dto.global.HeaderAddress;
import com.cintap.transport.ediparser.dto.segment.x12.B10;
import com.cintap.transport.ediparser.dto.segment.x12.K1;
import com.cintap.transport.ediparser.dto.segment.x12.L11;
import com.cintap.transport.ediparser.dto.segment.x12.LX;
import com.cintap.transport.ediparser.dto.segment.x12.MS3;
import com.cintap.transport.ediparser.dto.segment.x12.SE;
import com.cintap.transport.ediparser.dto.segment.x12.ST;


public class TransportationCarrierShipmentStatusMessage214 extends CintapEDIHeader implements Serializable{

	private static final long serialVersionUID = -3996304201612074292L;
	private ST stSegment;
	private SE se;
	private B10 b10;
	List<MS3> lstMS3Segment;
	private List<HeaderAddress> lstHeaderAddress;
	private List<LX> lstLXSegment;
	private List<L11> lstL11Segment;
	private List<K1> lstK1Segment;
	
	public TransportationCarrierShipmentStatusMessage214(ST stSegment, SE se, B10 b10,
			List<HeaderAddress> lstHeaderAddress, List<LX> lstLXSegment, List<L11> lstL11Segment,
			List<K1> lstK1Segment, List<MS3> lstMS3Segment) {
		super();
		this.stSegment = stSegment;
		this.se = se;
		this.b10 = b10;
		this.lstHeaderAddress = lstHeaderAddress;
		this.lstLXSegment = lstLXSegment;
		this.lstL11Segment = lstL11Segment;
		this.lstK1Segment = lstK1Segment;
		this.lstMS3Segment = lstMS3Segment;
	}
	public TransportationCarrierShipmentStatusMessage214() {
	}
	public ST getStSegment() {
		return stSegment;
	}
	public void setStSegment(ST stSegment) {
		this.stSegment = stSegment;
	}
	public SE getSe() {
		return se;
	}
	public void setSe(SE se) {
		this.se = se;
	}
	public B10 getB10() {
		return b10;
	}
	public void setB10(B10 b10) {
		this.b10 = b10;
	}
	public List<HeaderAddress> getLstHeaderAddress() {
		return lstHeaderAddress;
	}
	public List<MS3> getLstMS3Segment() {
		return lstMS3Segment;
	}
	public void setLstMS3Segment(List<MS3> lstMS3Segment) {
		this.lstMS3Segment = lstMS3Segment;
	}
	public void setLstHeaderAddress(List<HeaderAddress> lstHeaderAddress) {
		this.lstHeaderAddress = lstHeaderAddress;
	}
	public List<LX> getLstLXSegment() {
		return lstLXSegment;
	}
	public void setLstLXSegment(List<LX> lstLXSegment) {
		this.lstLXSegment = lstLXSegment;
	}
	public List<L11> getLstL11Segment() {
		return lstL11Segment;
	}
	public void setLstL11Segment(List<L11> lstL11Segment) {
		this.lstL11Segment = lstL11Segment;
	}
	public List<K1> getLstK1Segment() {
		return lstK1Segment;
	}
	public void setLstK1Segment(List<K1> lstK1Segment) {
		this.lstK1Segment = lstK1Segment;
	}
}
