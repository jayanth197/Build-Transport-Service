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
public class MS2 implements Serializable{
	private static final long serialVersionUID = -2470140985927753389L;

	private String ms201StandardCarrierAlphaCode;
	private String ms202EquipmentNumber;

	public MS2(ArrayList<Map> lst){
		for(Map ms2:lst) {
			String value=ms2.get("content").toString();
			switch(ms2.get("Id").toString()) {
			case "MS201":
				this.ms201StandardCarrierAlphaCode=value;
				break;
			case "MS202":
				this.ms202EquipmentNumber=value;
				break;

			default:
				// code block
			}
		}

	}

}

