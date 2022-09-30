package com.cintap.transport.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSourceSummary {
	
	private String source;
	private String createdDate;
	private Long trnCount;
	
	public TransactionSourceSummary() {
		
	}
	
	public TransactionSourceSummary(String source, String createdDate, Long trnCount) {
		this.source = source;
		this.createdDate = createdDate;
		this.trnCount = trnCount;
	}
}
