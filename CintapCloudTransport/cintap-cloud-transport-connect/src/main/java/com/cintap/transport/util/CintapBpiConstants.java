package com.cintap.transport.util;

public class CintapBpiConstants {
	private CintapBpiConstants() {
		
	}
	public static final String INVALID_FILE_EXTENSION = "Invalid File Extension";
	public static final String INTERNAL_TECHNICAL_ERROR="Internal Technical Error";
	public static final String INVALID_USER_NAME="Invalid User Name";
	public static final String UPDATE_PASS ="Password changed successfully";
	public static final String INVALID_USER="9001";
	public static final String INVALID_EDI_FILE="8001";
	public static final String MISSING_DELIMITER="8002";
	public static final String MISSING_ISA_QUALIFIER="8003";
	
	//Email constants
	public static final String WELCOME_EMAIL_TEMPLATE="WELCOME";
	public static final String TWO_FACTOR_AUTHENTICATION_TEMPLATE="TWO_FACTOR_AUTH";
	public static final String FORGOT_PASS_EMAIL_TEMPLATE="FORGOT_PASSWORD";
	public static final String PARTNER_INVITATION="PARTNER_INVITATION";
	public static final String CP_TYYPE_CODE="CP";
	//public static final String UPLOAD_URL = "c:/usr/local/tomcat9/webapps/images/";
	public static final String UPLOAD_URL = "/usr/local/tomcat9/webapps/images/";
	public static final String PROFILE_IMG_PATH = "profile_pic/";
	public static final String PRODUCT_IMG_PATH = "products/";
	public static final String EDI_FILE_GENERATION_LOCATION = "/logs/";
	public static final String EDI_FILE_XML_EXTENTION = ".xml";
	public static final String IMAGE_EXTENSION = ".png";
	public static final String[] ediSegments = {"ISA","GS","ST","IEA","GE","SE","N1","N3","N4"};
	public static final String[] edi850Segments = {"PO1","BEG"};
	public static final String[] edi810egments = {"IT1","BIG"};
	public static final String[] edi855Segments = {"PO1","BAK"};
	public static final String[] edi856Segments = {"UNH","UNT"};
	public static final String[] edi210Segments = {"LX","L0","L1","L3","L5","L7","N2","N9","B3"};
	public static final String[] edi214Segments = {"B10","L11","LX","AT7","MS1","MS2","Q7","AT8","N2"};
	public static final String DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_MM_DD_YYYY_HH_MM_SS = "MM-dd-yyyy HH:mm:ss";
	
	public static final String DATE_DDMMYYYY_HH_MM_SS = "ddMMyyyy-HH:MM:SS";
	public static final String DATE_DDMMYYYYHHMMSS = "ddMMyyyy-HHMMSS";
	public static final String DATE_YY_MM_DD = "yyMMdd";
	public static final String DATE_CCYY_MM_DD = "yyyyMMdd";
	public static final String HH_MM = "HH:mm";
	public static final String TIME_HHMM = "HHmm";
	public static final String TIME_HHMMSSSSS = "HHmmssSSS";
	
	public static final String DAY_OF_WEEK = "EEEE";
	public static final String PROCESS_FLOW_INBOUND="INBOUND";
	public static final String PROCESS_FLOW_OUTBOUND="OUTBOUND";
	public static final String PARTNER_SENDER="SENDER";
	public static final String PARTNER_RECEIVER="RECEIVER";
	public static final String SUCCESS="SUCCESS";
	public static final String FAIL="FAIL";
	public static final Integer STATUS=1;
	
	//Config properties:
	public static final String PF_CRON_EXPRESSION_KEY  = "pf_cron_expression";
	public static final String TWO_FA_EXPRITY_MINUTES_KEY  = "2fa_expiry_minutes";
	public static final String NTG_OCP_EXPRESSION_KEY  = "ntg_ocp_flag";
	public static final String JWT_AUTHORIZE_MATCHERS_KEY  = "jwt_auth_matchers";
	
	public static final String TRUE_FLAG = "TRUE";
	public static final String FALSE_FLAG = "FALSE";
	
	public static final String SUCCESS_CODE="000";
	public static final String FAIL_CODE="999";
	
	//JWT properties
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "devglan123r";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    //External API Header Properties
    public static final String HEADER_API_KEY = "API_KEY";
}
