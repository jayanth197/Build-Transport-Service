package com.cintap.transport.ediparser.dto.segment.x12;
import java.io.Serializable;

import com.cintap.transport.ediparser.dto.global.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SE implements Serializable {
	private static final long serialVersionUID = 5647324510646043122L;
	private String se01NumberOfIncludedSegments;
	private String se02TxnSetControlNumber;
	
	public SE(Transaction transaction){
		this.se01NumberOfIncludedSegments=(transaction.getSegment().size()+3)+"";
		this.se02TxnSetControlNumber=transaction.getControl()+"";
	}
}
