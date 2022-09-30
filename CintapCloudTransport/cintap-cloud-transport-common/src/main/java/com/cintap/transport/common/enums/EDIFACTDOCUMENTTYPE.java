package com.cintap.transport.common.enums;

public enum EDIFACTDOCUMENTTYPE {
	DESADV_ASN("DESADV","Document=\"PurchaseOrder\""),
	ORDERS("ORDERS","Document=\"Shipment\""),
	APERAK("APERAK","Document=\"GenericAcknowledgement\""),
	IFTMAN("IFTMAN","Document=\"ReceivingAdvice\""),
	SHIP_NOTICE("SHIP_NOTICE","Document=\"ShipNoticeManifest\"");
	
	private String type;
	private String text;
	
	private EDIFACTDOCUMENTTYPE(String type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public String getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}

}
