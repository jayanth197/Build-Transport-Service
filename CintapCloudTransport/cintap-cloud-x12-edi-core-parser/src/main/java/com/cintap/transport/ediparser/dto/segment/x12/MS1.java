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
public class MS1 implements Serializable{

	private static final long serialVersionUID = -2470140985927753389L;

	private String ms101CityName;
	private String ms102State;
	private String ms103Country;
	
	public MS1(ArrayList<Map> lst){
		for(Map ms1:lst) {
			String value=ms1.get("content").toString();
			switch(ms1.get("Id").toString()) {
			case "MS101":
				this.ms101CityName=value;
				break;
			case "MS102":
				this.ms102State=value;
				break;
			case "MS103":
				this.ms103Country=value;
				break;
			default:
				// code block
			}
		}
	}

}


