package com.cintap.transport.common.util;

import java.io.Serializable;

public class TransportResponseCodes implements Serializable{
	private static final long serialVersionUID = 4620564010823600952L;
	public static final String SUCCESS="0000";
	public static final String ERROR="9999";
	public static final String INTERNAL_TECHNICAL_ERROR="803";
	public static final String INVALID_FILE_EXTENSION="9001";
	public static final String INVALID_USER_CREATION="8001";

	public static final String DUPLICATE_EMAIL_ADDRESS="9001";
	public static final String INVALID_CREDENTIALS_CODE = "9002";
	public static final String DUPLICATE_PARTNER="9003";
	public static final String INVALID_CREDENTIALS = "Invalid Username or Password";
	public static final String PARTNER_ASSOCIATION_SUCCESS = "Partner Association Successful";
	
	public static final String SEARCH_PARTNER_FAIL_CODE = "9010";
	public static final String SEARCH_PARTNER_FAIL = "There is no record found";

	public static final String INVALID_EDI_FILE_CODE = "9011";
	public static final String INVALID_ISA_SEGMENT_CODE = "9012";
	public static final String INVALID_ISA_SEGMENT_SENDER_ID = "9013";
	public static final String INVALID_ISA_SEGMENT_RECEIVER_ID = "9014";
	public static final String INVALID_LOGIN_CREDENTIALS="8001";
	public static final String INVALID_ST_SEGMENT_EDI_TYPE = "9015";
	public static final String TRANSACTION_NOT_FOUND="9016";
	public static final String TRANSACTION_LOG_NOT_FOUND="9017";
	public static final String MISSING_DELIMITER_DETAILS = "9018";
	public static final String MISSING_SENDER_PARTNER_ID = "9019";
	public static final String MISSING_RECEIVER_PARTNER_ID = "9020";
	public static final String MISSING_MANUAL_DELIMITER = "9021";
	public static final String INVALID_2FA_SECURITY_CODE = "9022";
	public static final String EMAIL_NOT_FOUND = "9023";
	public static final String EXPIRED_2FA_SECURITY_CODE = "9024";
	public static final String DUPLICATE_EDI_DELIMITER = "9025";
	public static final String INVALID_XML_FILE = "9026";
	public static final String MISSING_API_KEY = "9027";
	public static final String INVALID_API_KEY = "9028";
	public static final String MISSING_PARTNER_ID = "9029";
	public static final String SENDER_AND_RECEIVER_PARTNER_ID_SAME = "9030";
}
