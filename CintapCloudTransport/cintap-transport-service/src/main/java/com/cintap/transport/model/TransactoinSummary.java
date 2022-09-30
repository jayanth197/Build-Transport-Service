package com.cintap.transport.model;

import lombok.Getter;

@Getter
public class TransactoinSummary {
	
	public TransactoinSummary() {
		
	}
	
	public TransactoinSummary(String trnType, Integer status, Long trnCount) {
		super();
		this.trnType = trnType;
		this.status = status;
		this.trnCount = trnCount;
	}

	private String trnType;
	private Integer status;
	private Long trnCount;
}
