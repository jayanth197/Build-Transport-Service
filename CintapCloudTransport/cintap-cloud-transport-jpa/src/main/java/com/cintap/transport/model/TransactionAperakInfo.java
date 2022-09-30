package com.cintap.transport.model;

import lombok.Getter;

@Getter
public class TransactionAperakInfo {
	public TransactionAperakInfo() {
		
	}

	public TransactionAperakInfo(Integer bpiLogId, String partnerTransactionId, Integer inOutId, String sentRawFile,
			String ackType) {
		super();
		this.bpiLogId = bpiLogId;
		this.partnerTransactionId = partnerTransactionId;
		this.inOutId = inOutId;
		this.sentRawFile = sentRawFile;
		this.ackType = ackType;
	}

	private Integer bpiLogId;
	private String partnerTransactionId;
	private Integer inOutId;
	private String sentRawFile;
	private String ackType;
}
