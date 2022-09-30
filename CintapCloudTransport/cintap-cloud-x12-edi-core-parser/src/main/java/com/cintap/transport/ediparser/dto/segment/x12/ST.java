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
public class ST implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String st01IdentifierCode;
	private String st02ControlNumber;
	
	public ST(Transaction transaction) {
		this.st01IdentifierCode=transaction.getDocType()+"";
		this.st02ControlNumber=transaction.getControl()+"";
	}
	public String getSt01IdentifierCode() {
		return st01IdentifierCode;
	}
	public void setSt01IdentifierCode(String st01IdentifierCode) {
		this.st01IdentifierCode = st01IdentifierCode;
	}
	public String getSt02ControlNumber() {
		return st02ControlNumber;
	}
	public void setSt02ControlNumber(String st02ControlNumber) {
		this.st02ControlNumber = st02ControlNumber;
	}

	
}
