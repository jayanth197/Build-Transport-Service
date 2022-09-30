package com.cintap.transport.common.enums;

public enum COMPONENTNAME {
	PARTNER("PARTNER"),
	TRANSACTION("TRANSACTION"),
	PROCESS("PROCESS"),
	PROCESSFLOW("PROCESSFLOW"),
	EDIDELIMITER("EDIDELIMITER"),
	EXTERNALUSER("EXTERNALUSER"),
	CONNECTION("CONNECTION"),
	BPORULES("BPORULES");
	private final String compName;
	
	private COMPONENTNAME(String componentName) {
		this.compName = componentName;
	}
	
	public String getComponentName() {
		return this.compName;
	}
}
