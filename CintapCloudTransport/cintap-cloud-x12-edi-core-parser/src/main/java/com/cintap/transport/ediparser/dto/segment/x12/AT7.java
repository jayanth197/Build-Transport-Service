package com.cintap.transport.ediparser.dto.segment.x12;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

public class AT7 implements Serializable {
	
	private static final long serialVersionUID = -1617155343170561420L;
	private String at701ShipmentStatusCode;
	private String at702ShipmentStatus;
	private String at703ShipmentAppointmentStatusCode;
	private String at704ShipmentStatus;
	private BigInteger at705Date;
	private BigInteger at706Time;
	private String at707TimeCode;
	
	
	public AT7(ArrayList<Map> lst){
		for(Map at7:lst) {
			String value=at7.get("content").toString();
			switch(at7.get("Id").toString()) {
			case "AT701":
				this.at701ShipmentStatusCode=value;
				break;
			case "AT702":
				this.at702ShipmentStatus=value;
				break;
			case "AT703":
				this.at703ShipmentAppointmentStatusCode=value;
				break;
			case "AT704":
				this.at704ShipmentStatus=value;
				break;
			case "AT705":
				this.at705Date=new BigDecimal(value).toBigInteger();;
				break;
			case "AT706":
				this.at706Time=new BigDecimal(value).toBigInteger();;
				break;
			case "AT707":
				this.at707TimeCode=value;
				break;

			default:
				// code block
			}
		}
		
	}


	public AT7() {
		// TODO Auto-generated constructor stub
	}


	public String getAt701ShipmentStatusCode() {
		return at701ShipmentStatusCode;
	}


	public void setAt701ShipmentStatusCode(String at701ShipmentStatusCode) {
		this.at701ShipmentStatusCode = at701ShipmentStatusCode;
	}


	public String getAt702ShipmentStatus() {
		return at702ShipmentStatus;
	}


	public void setAt702ShipmentStatus(String at702ShipmentStatus) {
		this.at702ShipmentStatus = at702ShipmentStatus;
	}


	public String getAt703ShipmentAppointmentStatusCode() {
		return at703ShipmentAppointmentStatusCode;
	}


	public void setAt703ShipmentAppointmentStatusCode(String at703ShipmentAppointmentStatusCode) {
		this.at703ShipmentAppointmentStatusCode = at703ShipmentAppointmentStatusCode;
	}


	public String getAt704ShipmentStatus() {
		return at704ShipmentStatus;
	}


	public void setAt704ShipmentStatus(String at704ShipmentStatus) {
		this.at704ShipmentStatus = at704ShipmentStatus;
	}


	public BigInteger getAt705Date() {
		return at705Date;
	}


	public void setAt705Date(BigInteger at705Date) {
		this.at705Date = at705Date;
	}


	public BigInteger getAt706Time() {
		return at706Time;
	}


	public void setAt706Time(BigInteger at706Time) {
		this.at706Time = at706Time;
	}


	public String getAt707TimeCode() {
		return at707TimeCode;
	}


	public void setAt707TimeCode(String at707TimeCode) {
		this.at707TimeCode = at707TimeCode;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}


