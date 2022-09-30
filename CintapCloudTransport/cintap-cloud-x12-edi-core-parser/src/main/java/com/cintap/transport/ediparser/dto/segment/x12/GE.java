package com.cintap.transport.ediparser.dto.segment.x12;
import java.io.Serializable;

import com.cintap.transport.ediparser.dto.global.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GE implements Serializable {
	private static final long serialVersionUID = 486050690706291075L;
	private String ge01NumberOfTxnIncluded;
	private String ge02GroupControlNumber;
	
	public GE(Group group){
		this.ge01NumberOfTxnIncluded="1";//group.getTransaction();
		this.ge02GroupControlNumber=group.getControl();
	}
}
