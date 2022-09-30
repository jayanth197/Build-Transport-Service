package com.cintap.transport.common.model;

import java.io.Serializable;
import java.util.List;

import com.cintap.transport.entity.trans.BpiLogDetail;

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
	private String transactionErrorReason;
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
	private Integer bpiLogId;
	private String ediStandard;
	private String receiverUnbId;
	private String senderUnbId;
	private String trnType;
	private Integer batchJobLogId;
	private String direction;
	private String trnStatus;
	private List<BpiLogDetail> lstLogDetails;
	private String ack997; 
	private String bgmMessageNumber;
	private String interchangeControlRef;
	private String creationDate;
	private String outboundRawFile;
	private String ackType;
	private String ackRefNum;
	private String parentTransactionNumber;
	private String transactionId;
	private String transType;
	private String senderCode;
	private String receiverCode;
}
