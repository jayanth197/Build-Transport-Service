package com.cintap.transport.common.enums;

public enum TRANSPORTLOG {
	IMPORT_FILE_REQUEST_RECEIVED("Import file request received"),
	FILE_PROCESS_INITIATED("File processing initiated"),
	FILE_PROCESS_ARCH_INITIATED("File Archive process is initiated"),
	FILE_DELETED_SUCCESS("File deleted successfully"),
	DELIMITER_SETUP_IDENTIFIED("Delimiter setup identified"),
	FILE_PARSING_SUCCESSFUL("File parsing successful with "),
	TRANSACTION_CREATED("Transaction is created"),
	IMPORT_PROCESS_COMPLETED("Import process completed and created numbe of transactions"),
	IMPORT_FILE_REQUEST_FAILED("Import file request failed"),
	DELIMITER_SETUP_MISSING("Delimiter setup is missing for Partner"),
	NO_OF_TRANSACTIONS_IDENTIFIED("No of transactions identified"),
	DUPLICATE_TRANSACTION("Duplicate Transaction"),
	MISSING_SENDER_PARTNER("Missing Sender Partner details"),
	MISSING_RECEIVER_PARTNER("Missing Receiver Partner details"),
	MISSING_REQUIRED_SEGMENTS("Invalid EDI file.  Missing required Segments"),
	FTP_CONNECTION_SUCCESS("FTP/SFTP connection verified  and Success"),
	FTP_CONNECTION_FAILED("FTP/SFTP connection verified and Failed"),
	FTP_CONNECTION_CLOSED("FTP/SFTP connection Closed"),
	PROCESS_INBOUND("Process Direction: INBOUND"),
	PROCESS_OUTBOUND("Process Direction: OUTBOUND"),
	MANUAL_PROCESS_INITIATED("Manual transaction process initiated"),
	API_REQUEST_INITIATED("API request initiated"), 
	MISSING_API_KEY("API Key is missing"),
	MISSING_PARTNER_ID("Partner Id is missing"),
	SENDER_AND_RECEIVER_PARTNER_ID_SAME("Sender and Receiver Partner Id should not be same"),
	INVALID_API_KEY("API Key/Partner Id is invalid"),
	INTERANL_ERROR("Internal Server error"),
	TRANSACTION_INITIATED("Transaction initiated"),
	DUPLICATE_ENTRY("Duplicate entry"),
	INVOICE_CREATED("Invoice created"),
	ASN_CREATED("Shipment created")	,
	SERVICE_CLASS_IDENTIFIED("Service Class Identified"),
	MAPPING_FAILED("Mapping Failed"),
	INITIATED_XML_CONVERTION("Initiated XML convertion"),
	INITIATED_EDIFACT_CONVERTION("Initiated EDIFACT convertion"),
	OUTBOUND_XML_FILE_GENERATED("Outbound XML file Generated"),
	SERVICE_CLASS_CONFIG_MISSING("Service class configuration is missing in DB"),
	TRANSACTION_NOT_FOUND("Transaction not found"),
	PLANT_CODE_NOT_FOUND("Plant code not found");
	private final String value;
	private TRANSPORTLOG(String value) {
		this.value=value;
	}
		
	public String getValue() {
		return value;
	}
}
