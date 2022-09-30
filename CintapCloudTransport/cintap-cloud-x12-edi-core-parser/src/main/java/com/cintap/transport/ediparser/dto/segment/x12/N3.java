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
public class N3 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String n301AddressInformation;
	private String n302AddressInformation;
	
	public N3(ArrayList<Map> lst){
		for(Map n3:lst) {
			String value=n3.get("content").toString();
			switch(n3.get("Id").toString()) {
			case "N301":
				this.n301AddressInformation=value;
				break;
			case "N302":
				this.n302AddressInformation=value;
				break;

			default:
				// code block
			}
		}
	}
}
