package com.cintap.transport.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.EdiDelimiter;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeaderReference;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipment;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipmentReference;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceHeader;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceReceiptPieces;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceReceiptPiecesItem;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.model.PackLineItem;
import com.cintap.transport.repository.common.EdiDelimiterRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReceivingAdviceOutboundEdifactParser {

	@Autowired
	EdiDelimiterRepository ediDelimiterRepository;
	
	@Autowired
	TransactionLogRepository transactionLogRepository;
	
	EdiDelimiter ediDelimiter = null;	
	String interchangeControlRef = null;
	String messageNumber = null;
	String type="PACK";
	int segmentCount=0;
	public String generateOutbound(ReceivingAdviceHeader receivingAdviceHeader, TransactionLog transactionLog, FileUploadParams fileUploadParams) {
		log.info("ReceivingAdviceOutboundEdifactParser : generateOutbound : " + transactionLog);
		
//		DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader=new DespatchAdviceShipNoticeHeader();		
		ediDelimiter = ediDelimiterRepository.findDelimiterByPartner(fileUploadParams.getSenderPartnerId(), fileUploadParams.getReceiverPartnerId(), "EDI", fileUploadParams.getFileType());
		
		segmentCount = 0;
				
		StringBuilder edifactData = new StringBuilder();
		try {
			
			interchangeControlRef = TransportCommonUtility.genrateRandomNumber();
			messageNumber = TransportCommonUtility.generateControlNumber(receivingAdviceHeader.getMessageReference());
			transactionLogRepository.updateStControlId(transactionLog.getBpiLogId(), interchangeControlRef);
			edifactData.append(buildUNASegment(receivingAdviceHeader,transactionLog))
			.append(buildUNBSegment(receivingAdviceHeader,transactionLog))
			.append(buildUNHSegment(receivingAdviceHeader,transactionLog))
			.append(buildBGMSegment(receivingAdviceHeader,transactionLog))
			.append(buildDTMSegment(receivingAdviceHeader,transactionLog))
			.append(buildDTM1Segment(receivingAdviceHeader,transactionLog))
			.append(buildDTM2Segment(receivingAdviceHeader,transactionLog))
			.append(buildFTXSegment(receivingAdviceHeader,transactionLog))
			.append(buildFTX1Segment(receivingAdviceHeader,transactionLog))
			.append(buildRFFSegment(receivingAdviceHeader,transactionLog))
			.append(buildLineItemSegment(receivingAdviceHeader,transactionLog))			
			.append(buildUNTSegment(receivingAdviceHeader,transactionLog))
			.append(buildUNZSegment(receivingAdviceHeader,transactionLog));
			log.info(transactionLog.getBpiLogId() + " :: Completed Started EDI Convertion");
		} catch (Exception exception) {
			log.info(transactionLog.getBpiLogId() + " :: Got Exception In EDI Convertion :: "+exception.getMessage());
		}

		log.info("ReceivingAdviceOutboundEdifactParser : generated output : " + edifactData);
		return edifactData.toString();
	}

	private StringBuilder buildUNASegment(ReceivingAdviceHeader receviAdviceHeader , TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNA:+.? '\n");
		return unaSegment;
	}

	private StringBuilder buildUNBSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNB")
		.append(ediDelimiter.getFieldDelimiter())
		.append("UNOA")
		.append(ediDelimiter.getFieldSeparator())
		.append("4")
		.append(ediDelimiter.getFieldDelimiter())
		.append(transactionLog.getSenderPartnerCode())
		.append(ediDelimiter.getFieldSeparator())
		.append(transactionLog.getSenderIsa())
		.append(ediDelimiter.getFieldDelimiter())
		.append(transactionLog.getReceiverPartnerCode())
		.append(ediDelimiter.getFieldSeparator())
		.append(transactionLog.getReceiverIsa())
		.append(ediDelimiter.getFieldDelimiter())
		.append(TransportCommonUtility.getDocumentCreationDateAndTime())
		.append(ediDelimiter.getFieldDelimiter())
		.append(interchangeControlRef)
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}

	private StringBuilder buildUNHSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNH")
		.append(ediDelimiter.getFieldDelimiter())
		.append(messageNumber)
		.append(ediDelimiter.getFieldDelimiter())
		.append("IFTMAN")
		.append(ediDelimiter.getFieldSeparator())
		.append("1")
		.append(ediDelimiter.getFieldSeparator())
		.append("921")
		.append(ediDelimiter.getFieldSeparator())
		.append("UN")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildBGMSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("BGM")
		.append(ediDelimiter.getFieldDelimiter())
		.append("781")
		.append(ediDelimiter.getFieldDelimiter())
		.append(receivingAdviceHeader.getLstReceivingAdviceReceipt().get(0).getPurchaseOrderNumber())
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildDTMSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+137")
		.append(ediDelimiter.getFieldSeparator())
		.append(TransportCommonUtility.convertToDateFormat(receivingAdviceHeader.getDocumentCreationDate(),"yyyy-MM-dd'T'HH:mm:ss","yyyyMMdd"))
		.append(ediDelimiter.getFieldSeparator())
		.append("102")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildDTM1Segment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+110")
		.append(ediDelimiter.getFieldSeparator())
		.append(TransportCommonUtility.convertToDateFormat(receivingAdviceHeader.getDocumentCreationDate(),"yyyy-MM-dd'T'HH:mm:ss","yyyyMMddHHmmss"))
		.append(ediDelimiter.getFieldSeparator())
		.append("204")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
		
	}
	
	private StringBuilder buildDTM2Segment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+11")
		.append(ediDelimiter.getFieldSeparator())
		.append(TransportCommonUtility.convertToDateFormat(receivingAdviceHeader.getDocumentCreationDate(),"yyyy-MM-dd'T'HH:mm:ss","yyyyMMddHHmmss"))
		.append(ediDelimiter.getFieldSeparator())
		.append("204")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
		
	}
	
	private StringBuilder buildFTXSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("FTX")
		.append(ediDelimiter.getFieldDelimiter())
		.append("DEL")
		.append(ediDelimiter.getFieldDelimiter())
		.append(ediDelimiter.getFieldDelimiter())
		.append(ediDelimiter.getFieldDelimiter())
		.append("REC")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildFTX1Segment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("FTX")
		.append(ediDelimiter.getFieldDelimiter())
		.append("ALL")
		.append(ediDelimiter.getFieldDelimiter())
		.append(ediDelimiter.getFieldDelimiter())
		.append(ediDelimiter.getFieldDelimiter())
		.append("OT")
		.append(ediDelimiter.getFieldSeparator())
		.append("N")
		.append(ediDelimiter.getSegmentDelimiter());		
		++segmentCount;
		return unaSegment;
	}
	private StringBuilder buildRFFSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("RFF")
		.append(ediDelimiter.getFieldDelimiter())
		.append("ON")
		.append(ediDelimiter.getFieldDelimiter())
		.append(receivingAdviceHeader.getLstReceivingAdviceReceipt().get(0).getPurchaseOrderNumber())
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
		
	private StringBuilder buildLineItemSegment(ReceivingAdviceHeader receivingAdviceHeader,
			TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		Map<String,Integer> lineMapper=new HashMap<String,Integer>();
		int i=0;
		List<PackLineItem> lstPackLineItem=new ArrayList<>();	
		for(ReceivingAdviceReceiptPieces lineItem:receivingAdviceHeader.getLstReceivingAdviceReceipt().get(0).getLstReceivingAdviceReceiptPieces()) {
			for(ReceivingAdviceReceiptPiecesItem lstPieces:lineItem.getLstReceivingAdviceReceiptPiecesItems()) {				
				
					String partNo=lstPieces.getPartNumber();
					
					unaSegment.append("GID")
					.append(ediDelimiter.getFieldDelimiter())
					.append(++i)
					.append(ediDelimiter.getSegmentDelimiter())
					
					.append("PIA")
					.append(ediDelimiter.getFieldDelimiter())
					.append("5")
					.append(ediDelimiter.getFieldDelimiter())
					.append(partNo)
					.append(ediDelimiter.getFieldSeparator())
					.append("VP")
					.append(ediDelimiter.getSegmentDelimiter())
					
					.append("FTX")
					.append(ediDelimiter.getFieldDelimiter())
					.append("ALL")
					.append(ediDelimiter.getFieldDelimiter())
					.append(ediDelimiter.getFieldDelimiter())
					.append(ediDelimiter.getFieldDelimiter())
					.append("CC")
					.append(getConditionCode(lstPieces.getConditionCode()))
					.append(ediDelimiter.getSegmentDelimiter())
					
					.append("MEA")
					.append(ediDelimiter.getFieldDelimiter())
					.append("WT")
					.append(ediDelimiter.getFieldDelimiter())
					.append(ediDelimiter.getFieldDelimiter())					
					.append("KGM")
					.append(ediDelimiter.getFieldSeparator())
					.append(lineItem.getGrossWeightKg())
					.append(ediDelimiter.getSegmentDelimiter())
					
					.append("EQN")
					.append(ediDelimiter.getFieldDelimiter())	
					.append(lstPieces.getTotalQuantity())
					.append(ediDelimiter.getSegmentDelimiter())
					;		
					segmentCount+=5;
			}
		
			
		}
		
		return unaSegment;
	}

	private StringBuilder buildUNTSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNT")
		.append(ediDelimiter.getFieldDelimiter())
		.append(segmentCount)
		.append(ediDelimiter.getFieldDelimiter())
		.append(messageNumber)	
		.append(ediDelimiter.getSegmentDelimiter());
		return unaSegment;
	}
	
	private StringBuilder buildUNZSegment(ReceivingAdviceHeader receivingAdviceHeader,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNZ")
		.append(ediDelimiter.getFieldDelimiter())
		.append("1")
		.append(ediDelimiter.getFieldDelimiter())
		.append(interchangeControlRef)		
		.append(ediDelimiter.getSegmentDelimiter());
		return unaSegment;
	}	

	private String generateControlNumber(String refNumber) {
		return StringUtils.leftPad(refNumber, 8, "0");
	}
	
	
	private String getMessageNumber(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		String MessageNumber="";
		for (DespatchAdviceShipNoticeHeaderReference ref:despatchAdviceShipNoticeHeader.getLstDesadvShipNoitceHeaderReference()) {			
			if(ref.getKey().equals("@UHN01")) {
				MessageNumber=ref.getValue();
			}
		}				
		return MessageNumber;
	}
	
	private String getPickPackDate(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		String date="";
		for (DespatchAdviceShipNoticeShipment shipment:despatchAdviceShipNoticeHeader.getLstDesadvShipNoitceShipment()) {			
			date=TransportCommonUtility.convertToDateFormat(shipment.getPackagedTime(),"yyyy-MM-dd'T'HH:mm:ss","yyyyMMddHHmmss");			
		}				
		return date;
	}
	
	private String getShipmentNo(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader,String refType) {
		String shipmentNo="";
		DespatchAdviceShipNoticeShipment ref=despatchAdviceShipNoticeHeader.getLstDesadvShipNoitceShipment().get(0);
		for (DespatchAdviceShipNoticeShipmentReference ref1:ref.getLstDesadvShipNoitceShipmentReference()) {			
			if(ref1.getKey().equals(refType)) {
				shipmentNo=ref1.getValue();
			}
		}				
		
		return shipmentNo;
	}
	
	private String getStatus(String responseCode) {
		String value = "";
		switch (responseCode) {
			case "Packaged":
				value = "PACK";
				break;
			case "Forwarded":
				value = "FORWARD";
				break;
			case "Picked":
				value = "PICK";
				break;
			default:
				break;
		}
		return value;
	}
	
	private String getConditionCode(String code) {
		String value = "";
		switch (code) {
			case "1":
				break;
			case "2":
				value = ":I";
				break;
			case "3":
				value = ":2";
				break;
			case "4":
				value = ":4";
				break;
			case "5":
				value = ":5";
				break;
			default:
				break;
		}
		return value;
	}
}
