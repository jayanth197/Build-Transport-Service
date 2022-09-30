package com.cintap.transport.ediparser.dto.segment.x12;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MS3 implements Serializable{

	private static final long serialVersionUID = -2470140985927753389L;

	private String ms301StandardCarrierAlphaCode;
	private String ms302RoutingSequenceCode;
	private String ms303CityName;
	private String ms304TransportationMethodOrTypeCode;
	private String ms305StateOrProvinceCode;	

	public MS3(ArrayList<Map> lst) {
		for(Map ms3:lst) {
			String value=ms3.get("content").toString();
			switch(ms3.get("Id").toString()) {
			case "MS301":
				this.ms301StandardCarrierAlphaCode=value;
				break;
			case "MS302":
				this.ms302RoutingSequenceCode=value;
				break;
			case "MS303":
				this.ms303CityName=value;
				break;
			case "MS304":
				this.ms304TransportationMethodOrTypeCode=value;
				break;
			case "MS305":
				this.ms305StateOrProvinceCode=value;
				break;

			default:
				// code block
			}
		}
	}

}


