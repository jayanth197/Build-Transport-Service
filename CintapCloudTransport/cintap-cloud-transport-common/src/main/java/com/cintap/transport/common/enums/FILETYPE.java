package com.cintap.transport.common.enums;

public enum FILETYPE {

	TEXT_XML("text/xml"), 
	TXT_PLAIN("text/plain"), 
	EDI_OCTET("application/octet-stream"),
	XML("XML"),
	FF("FF"),
	EDI("EDI"),
	EDI_FACT("EDIFACT"),
	TXT("TXT"),
	DAT("DAT"),
	S2696("2696");

	private String value;

	private FILETYPE(String fileType) {
		this.value = fileType;
	}

	public String getValue() {
		return value;
	}
}
