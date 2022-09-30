package com.cintap.transport.common.enums;

public enum DIRECTION {
	INBOUND("INBOUND"),
	OUTBOUND("OUTBOUND");
	private String directions;
	private DIRECTION(String direction) {
		this.directions=direction;
	}
	public String getDirection() {
		return this.directions;
	}
}
