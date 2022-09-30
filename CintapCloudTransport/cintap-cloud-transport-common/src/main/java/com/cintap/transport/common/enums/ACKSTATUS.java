package com.cintap.transport.common.enums;

public enum ACKSTATUS {
	RECEIVED(1),
	RECEIVED_SENT(2),
	ACCEPTED(3),
	ACCEPTED_SENT(4),
	REJECTED(5),
	REJECTED_SENT(6),
	ERROR(7),
	ERROR_SENT(8);

	private int code;

	private ACKSTATUS(int code) {
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}
}
