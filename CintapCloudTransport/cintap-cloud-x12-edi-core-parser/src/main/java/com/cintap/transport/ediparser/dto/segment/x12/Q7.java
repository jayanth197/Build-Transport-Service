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
public class Q7 implements Serializable{

	private static final long serialVersionUID = -2470140985927753389L;

	private String q701LadingExceptionCode;
	private String q702PackagingFormCode;
	private String q703LadingQuantity;

	public Q7(ArrayList<Map> lst){
		for(Map q7:lst) {
			String value=q7.get("content").toString();
			switch(q7.get("Id").toString()) {
			case "Q701":
				this.q701LadingExceptionCode=value;
				break;
			case "Q702":
				this.q702PackagingFormCode=value;
				break;
			case "Q703":
				this.q703LadingQuantity=value;
				break;			
			}
		}


	}
}



