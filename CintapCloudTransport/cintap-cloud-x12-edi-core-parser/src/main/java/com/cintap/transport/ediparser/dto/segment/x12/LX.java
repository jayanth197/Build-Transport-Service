package com.cintap.transport.ediparser.dto.segment.x12;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LX implements Serializable{
	private static final long serialVersionUID = -2470140985927753389L;

	private String Lx01AssignedNumber;
	private AT7 at7Segment;
	private MS1 ms1Segment;
	private MS2 ms2Segment;	
	
	private List<L11> lstL11Segment;
	private List<Q7> lstQ7Segment;
	private List<AT8> lstAT8Segment;
	
	
	
	public LX(Map lst) {
        this.Lx01AssignedNumber = lst.get("content").toString();
	}
	public String getLx01AssignedNumber() {
		return Lx01AssignedNumber;
	}
	public void setLx01AssignedNumber(String lx01AssignedNumber) {
		Lx01AssignedNumber = lx01AssignedNumber;
	}
	public AT7 getAt7Segment() {
		return at7Segment;
	}
	public void setAt7Segment(AT7 at7Segment) {
		this.at7Segment = at7Segment;
	}
	public MS1 getMs1Segment() {
		return ms1Segment;
	}
	public void setMs1Segment(MS1 ms1Segment) {
		this.ms1Segment = ms1Segment;
	}
	public MS2 getMs2Segment() {
		return ms2Segment;
	}
	public void setMs2Segment(MS2 ms2Segment) {
		this.ms2Segment = ms2Segment;
	}
	public List<L11> getLstL11Segment() {
		return lstL11Segment;
	}
	public void setLstL11Segment(List<L11> lstL11Segment) {
		this.lstL11Segment = lstL11Segment;
	}
	public List<Q7> getLstQ7Segment() {
		return lstQ7Segment;
	}
	public void setLstQ7Segment(List<Q7> lstQ7Segment) {
		this.lstQ7Segment = lstQ7Segment;
	}
	public List<AT8> getLstAT8Segment() {
		return lstAT8Segment;
	}
	public void setLstAT8Segment(List<AT8> lstAT8Segment) {
		this.lstAT8Segment = lstAT8Segment;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

		

}