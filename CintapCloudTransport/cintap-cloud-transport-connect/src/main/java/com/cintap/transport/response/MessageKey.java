package com.cintap.transport.response;

public class MessageKey {
	private MessageKey() {
		
	}
	public static final String INVALID_CREDENTIALS_KEY="login.fail.message";
	public static final String ONBOARDING_SUCCESS_KEY = "onboarding.success.message";
	public static final String DUPLICATE_EMAIL_KEY = "email.duplicate.message";
	public static final String INTERNAL_TECHNICAL_ERROR_KEY = "internal.technical.error";
	public static final String INVALID_EDI_FILE = "invalid.edi.file";
	public static final String EDI_850_SUCCESS="save.edi.850";
	public static final String PARTNER_INVITATION_SUCCESS="partner.invitation.success";
	public static final String INVALID_EDI_FILE_KEY="edi.fail.message";
	public static final String INVALID_ISA_SEGMENT_KEY="edi.isa.segment.fail";
	public static final String INVALID_ISA_SENDERID_KEY="edi.isa.sender.fail";
	public static final String INVALID_ISA_RECEIVERID_KEY="edi.isa.receiver.fail";
	public static final String INVALID_ST_SEGMENT_EDI_TYPE_KEY="edi.st.edi.type.mismatch";
	public static final String TXN_NOT_FOUND="trn.not.found";
	public static final String TRANSACTION_LOG_NOT_FOUND="trn.log.not.found";
	public static final String CONNECTION_SAVE_SUCCESS="conn.success";
	public static final String MISSING_EDI_DELEMETER="edi.missing.delimiter";
	public static final String EXTERNAL_SAVE_SUCCESS="external.success";
	public static final String DUPLICATE_PARTNER="duiplicate.partner";
	public static final String MISSING_SENDER_PARTNER="ff.sender.missing";
	public static final String MISSING_RECEIVER_PARTNER="ff.receiver.missing";
	public static final String APPLICATION_SAVE_SUCCESS="application.success";
}
