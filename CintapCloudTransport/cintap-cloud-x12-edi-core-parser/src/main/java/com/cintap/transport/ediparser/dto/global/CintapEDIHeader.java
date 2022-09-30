package com.cintap.transport.ediparser.dto.global;

import com.cintap.transport.ediparser.dto.segment.x12.GE;
import com.cintap.transport.ediparser.dto.segment.x12.GS;
import com.cintap.transport.ediparser.dto.segment.x12.IEA;
import com.cintap.transport.ediparser.dto.segment.x12.ISA;

public class CintapEDIHeader {
	
	private ISA isaSegment;
	private GS gsSegment;
	private GE geSegment;
	private IEA ieaSegment;	
	
	public ISA getIsaSegment() {
		return isaSegment;
	}
	public void setIsaSegment(ISA isaSegment) {
		this.isaSegment = isaSegment;
	}
	public GS getGsSegment() {
		return gsSegment;
	}
	public void setGsSegment(GS gsSegment) {
		this.gsSegment = gsSegment;
		
	}
	public GE getGeSegment() {
		return geSegment;
	}
	public void setGeSegment(GE geSegment) {
		this.geSegment = geSegment;
	}
	public IEA getIeaSegment() {
		return ieaSegment;
	}
	public void setIeaSegment(IEA ieaSegment) {
		this.ieaSegment = ieaSegment;
	}
	
}
