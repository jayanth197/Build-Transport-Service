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
public class L11 implements Serializable{
	
	private static final long serialVersionUID = -2470140985927753389L;
	
	private String L1101ReferenceIdentification;
	private String L1102ReferenceIdentificationQualifier;
	
	public L11(ArrayList<Map> lst){
		for(Map l11:lst) {
			String value=l11.get("content").toString();
			switch(l11.get("Id").toString()) {
			case "L1101":
				this.L1101ReferenceIdentification=value;
				break;
			case "L1102":
				this.L1102ReferenceIdentificationQualifier=value;
				break;

			default:
				// code block
			}
		}
	
		
	}

}



