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
public class B10 implements Serializable{
	private static final long serialVersionUID = -2470140985927753389L;
	
	private String b1001ReferenceIdentification;
	private String b1002ShipmentIdentificationNumber;
	private String b1003StandardCarrierAlphaCode;
	
	
	
	public B10(ArrayList<Map> lst){
		for(Map at7:lst) {
			String value=at7.get("content").toString();
			switch(at7.get("Id").toString()) {
			case "B1001":
				this.b1001ReferenceIdentification=value;
				break;
			case "B1002":
				this.b1002ShipmentIdentificationNumber=value;
				break;
			case "B1003":
				this.b1003StandardCarrierAlphaCode=value;
				break;
			

			default:
				// code block
			}
		}
		
	}

}


