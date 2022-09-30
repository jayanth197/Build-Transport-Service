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
public class N1 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String n101IdentifierCode;
	private String n102Name;
	private String n103CodeQualifier;
	private String n104IdentificationCode;

	public N1(ArrayList<Map> lst){
		for(Map at7:lst) {
			String value=at7.get("content").toString();
			switch(at7.get("Id").toString()) {
			case "N101":
				this.n101IdentifierCode=value;
				break;
			case "N102":
				this.n102Name=value;
				break;

			case "N103":
				this.n103CodeQualifier=value;
				break;
			case "N104":
				this.n104IdentificationCode=value;
				break;

			default:
				// code block
			}
		}
	}
}
