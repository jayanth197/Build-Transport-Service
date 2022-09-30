package com.cintap.transport.util;

import org.jboss.logging.MDC;

public class CintapMDC {
	
	private static String PARTNER_ID_KEY="PARTNERID";
	private static String CINTAP_REF_ID_KEY="REFID";
	private static String PROCESS_ID_KEY="PROCESSID";
	private static String CONNECTION_ID_KEY="CONNECTIONID";
	private static String MAPS_ID_KEY="MAPSID";
	
	public static void putMDC(String partnerId,String cintapRefId) {
		MDC.put(PARTNER_ID_KEY,partnerId); 
		MDC.put(CINTAP_REF_ID_KEY,cintapRefId);
	}
	
	public static void putPFMDC(String partnerId,int processId,int connectionId,int mapsId) {
		MDC.put(PARTNER_ID_KEY,partnerId); 
		MDC.put(PROCESS_ID_KEY,processId);
		MDC.put(CONNECTION_ID_KEY,connectionId);
		MDC.put(MAPS_ID_KEY,mapsId);
	}
	
	public static void clearMDC() {
		MDC.clear();
	}
	
	public static String generateCintapMDCUniqueId() {
		return  String.valueOf(Math.random());
	}
}
