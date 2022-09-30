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
public class K1 implements Serializable{

	private static final long serialVersionUID = -2470140985927753389L;
	private String K101FreeFormMessage;
	private String K102FreeFormMessage;
	public K1(ArrayList<Map> lst){
		for(Map at7:lst) {
			String value=at7.get("content").toString();
			switch(at7.get("Id").toString()) {
			case "K101":
				this.K101FreeFormMessage=value;
				break;
			case "K102":
				this.K102FreeFormMessage=value;
				break;

			}
		}

	}

}



