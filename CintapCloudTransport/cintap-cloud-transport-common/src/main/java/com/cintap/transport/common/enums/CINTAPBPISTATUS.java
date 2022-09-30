package com.cintap.transport.common.enums;

/**
 * 
 * @author Narasimhulu Chary
 * 
 *  Rule: At UI level, all status between OTC, BPI and Partner.  Before add any new status,
 *        check at UI level and add new status with appropriate status id.  Otherwise, it will
 *        cause problem while displaying in UI
 */

public enum CINTAPBPISTATUS {
	NEW(1,"NEW"),
	ERROR(2,"ERROR"),
	PROCESSED(3,"PROCESSED"),
	ACTIVE(5,"ACTIVE"),
	IN_PROGRESS(12,"IN_PROGRESS"),
	SUCCESS(9,"SUCCESS"),
	BUSINESS_RULE_FAILED(11, "BUSINESS_RULE_FAILED"),
	OUTBOUND_NA(13, "Outbound NA");
	
	private final int statusId;
	private final String statusValue;
	
	private CINTAPBPISTATUS(int statusId,String statusValue) {
		this.statusId = statusId;
		this.statusValue=statusValue;
	}
	
	public int getStatusId() {
		return this.statusId;
	}
	
	public String getStatusValue() {
		return this.statusValue;
	}
}
