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
public class AT8 implements Serializable{
private static final long serialVersionUID = -2470140985927753389L;
	
	private String at801WeightQualifier;
	private String at802WeightUnitCode;
	private String at803Weight;
    private String at804LadingQty;
    private String at805;
    private String at806VolumeUnitCode;
    private String at07Volume;
    
	public AT8(ArrayList<Map> lst){
		for(Map at8:lst) {
			String value=at8.get("content").toString();
			switch(at8.get("Id").toString()) {
			case "AT801":
				this.at801WeightQualifier=value;
				break;
			case "AT802":
				this.at802WeightUnitCode=value;
				break;
			case "AT803":
				this.at803Weight=value;
				break;
			case "AT804":
				this.at803Weight=value;
				break;
			case "AT805":
				this.at805=value;
				break;
			case "AT806":
				this.at806VolumeUnitCode=value;
				break;
			case "AT807":
				this.at07Volume=value;
				break;	
			}
		}
		
	}

}


	


