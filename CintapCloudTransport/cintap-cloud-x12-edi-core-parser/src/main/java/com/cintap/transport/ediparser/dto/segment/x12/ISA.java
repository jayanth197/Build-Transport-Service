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
public class ISA implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isa05SenderQualifier;
	private String isa13InterchangeControlNumber;
	private String isa11StandardsId;
	private String isa03SecurityQualifier;
	private String isa07ReceiverQualifier;
	private String isa01AuthorizationQualifier;
	private String isa08ReceiverId;
	private String isa06SenderId;
	private String isa02AuthorizationInformation;
	private String isa14AcknowledgmentRequested;
	private String isa04SecurityInformation;
	private String isa10Time;
	private String isa09Date;
	private String isa15TestIndicator;
	private String isa12Version;
	private String isa16Indicator;
	
	public ISA(Interchange ediData) {
		this.isa01AuthorizationQualifier = ""+ediData.getAuthorizationQual();
		this.isa02AuthorizationInformation = ""+ediData.getAuthorization();
		this.isa03SecurityQualifier = ediData.getSecurityQual()+"";
		this.isa04SecurityInformation = ediData.getSecurity()+"";
		this.isa05SenderQualifier = ediData.getSender().getAddress().getQual();
		this.isa06SenderId = ediData.getSender().getAddress().getID()+"";
		this.isa07ReceiverQualifier =ediData.getReceiver().getAddress().getQual();
		this.isa08ReceiverId = ediData.getReceiver().getAddress().getID()+"";
		this.isa09Date = ediData.getDate()+"";
		this.isa10Time = ediData.getTime()+"";
		this.isa11StandardsId = ediData.getStandardsId();
		this.isa12Version = ediData.getVersion()+"";
		this.isa13InterchangeControlNumber = ediData.getControl()+"";
	//	this.isa14AcknowledgmentRequested = 0;
	//	this.isa15TestIndicator = ediData.size()>15 ? ediData.get(15):null;
	//	this.isa16Indicator = ediData.size()>16 ? ediData.get(16):null;
	}
}
