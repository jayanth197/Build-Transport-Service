package com.cintap.transport.aperak.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AperakData {
	/**
	 * 
	 */
	private Integer bpiLogId;
	
	private String senderAddressId;

	private String senderAddressQual;
	
	private String receiverAddressId;
	
	private String receiverAddressQual;
	
	private String ackType;
	
	private String documentName;

	private String messageReferenceNumber;

	private String refUnh01;

	private String refSource;

	private String identification;
	
	private String referenceNumberAck;
	
	private String responseCode;

	private String responseReason;
	
	private String referenceNumber;

	private String bgmReferenceNumber;
	
	private String documentCreationDate;

	private String documentCreationTime;

}