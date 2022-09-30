package com.cintap.transport.model;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadParams implements Serializable{
	private static final long serialVersionUID = -8734566090307250441L;
	private String fileName;
	private String partnerId;
	private String createdBy;
	private String ediType;
	private String ediVersion;
	private String senderPartnerId;
	private String senderIsaId;
	private String senderPartnerDisplayName;
	private String receiverPartnerId;
	private String receiverIsaId;
	private String receiverPartnerDisplayName;
	private String actualFileType;
	private String fileType;
	private String parseType;
	private Integer batchId;
	private String rawFile;
	private String source;
	private Integer bpiHeaderLogId;
	private Integer groupId;
	private Integer txnCount;
	private String batchStatus;
	private Integer fileCount;
	private Integer processId;
	private Integer processFlowId;
	private String invoiceStatus;
	private String shipmentDate;
	private BigInteger bpiLogId;
	private String ediStandard;
	private String receiverUnbId;
	private String senderUnbId;
	private String trnType;
	private Integer batchJobLogId;
	private String direction;
	private String trnStatus;
}
