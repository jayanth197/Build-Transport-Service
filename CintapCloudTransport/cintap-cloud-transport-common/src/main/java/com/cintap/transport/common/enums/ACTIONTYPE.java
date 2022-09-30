package com.cintap.transport.common.enums;

public enum ACTIONTYPE {
	CREATE("CREATE"),
	UPDATE("UPDATE"),
	DELETE("DELETE");
	
	private String action;
	
	private ACTIONTYPE(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}
}
