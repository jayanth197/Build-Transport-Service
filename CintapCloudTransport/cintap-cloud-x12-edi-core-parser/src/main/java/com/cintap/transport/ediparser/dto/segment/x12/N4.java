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
public class N4 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String n401CityName;
	private String n402State;
	private String n403PostalCode;
	private String n404CountryCode;

	public N4(ArrayList<Map> lst){
		for(Map n4:lst) {
			String value=n4.get("content").toString();
			switch(n4.get("Id").toString()) {
			case "N401":
				this.n401CityName=value;
				break;
			case "N402":
				this.n402State=value;
				break;

			case "N403":
				this.n403PostalCode=value;
				break;
			case "N404":
				this.n404CountryCode=value;
				break;

			default:
				// code block
			}
		}
	}
}

