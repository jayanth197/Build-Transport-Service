package com.cintap.transport.ediparser.dto.segment.x12;
import java.io.Serializable;

import com.cintap.transport.ediparser.dto.global.Interchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IEA implements Serializable {
	private static final long serialVersionUID = 6433787085857015487L;
	private String iea01NumberOfFunctionalGroups;
	private String iea02InterchangeControlNumber;
	
	public IEA(Interchange interchange){
		this.iea01NumberOfFunctionalGroups="1";//interchange.getGroup();
		this.iea02InterchangeControlNumber=interchange.getControl()+"";
	}
}
