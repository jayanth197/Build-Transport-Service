package com.cintap.transport.ediparser.dto.segment.x12;
import java.io.Serializable;

import com.cintap.transport.ediparser.dto.global.Group;
import com.cintap.transport.ediparser.dto.global.Interchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GS implements Serializable {
	private static final long serialVersionUID = 1L;
	private String gs07ResponsibleAgencyCode;
	private String gs04Date;
	private String gs01FunctionalIdentifierCode;
	private String gs05Time;
	private String gs02ApplicationSenderCode;
	private String gs08Version;
	private String gs06GroupControlNumber;
	private String gs03ApplicationReceiverCode;

	public GS(Interchange interchange) {
		Group group=interchange.getGroup();
		this.gs01FunctionalIdentifierCode = group.getGroupType();
		this.gs02ApplicationSenderCode = group.getApplSender();
		this.gs03ApplicationReceiverCode = group.getApplReceiver();
		this.gs04Date = group.getDate();
		this.gs05Time = group.getTime();
		this.gs06GroupControlNumber = group.getControl();
		this.gs07ResponsibleAgencyCode = group.getStandardCode();
		//this.gs08Version = interchange.size()>8?interchange.get(8):null;
	}
}
