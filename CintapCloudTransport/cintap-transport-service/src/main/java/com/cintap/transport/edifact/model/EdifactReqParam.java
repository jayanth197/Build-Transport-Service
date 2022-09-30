package com.cintap.transport.edifact.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EdifactReqParam {
	private String senderPartnerId;
	private String receiverPartnerId;
	private String ediFactData;
}
