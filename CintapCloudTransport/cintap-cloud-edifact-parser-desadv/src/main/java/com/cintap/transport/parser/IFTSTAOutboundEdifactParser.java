package com.cintap.transport.parser;

import com.cintap.transport.common.enums.DATEFORMAT;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.EdiDelimiter;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipment;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipmentReference;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.common.EdiDelimiterRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IFTSTAOutboundEdifactParser {

	@Autowired
	EdiDelimiterRepository ediDelimiterRepository;
	
	@Autowired
	TransactionLogRepository transactionLogRepository;
	
	EdiDelimiter ediDelimiter = null;	
	String interchangeControlRef = null;
	String messageNumber = null;
	String type="PACK";
	int segmentCount=0;
	public String generateOutbound(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader, TransactionLog transactionLog, FileUploadParams fileUploadParams) {
		log.info("AperakOutboundXmlParser : generateOutbound : " + transactionLog);
				
		ediDelimiter = ediDelimiterRepository.findDelimiterByPartner(fileUploadParams.getSenderPartnerId(), fileUploadParams.getReceiverPartnerId(), "EDI", fileUploadParams.getFileType());
		
		segmentCount = 0;				
		StringBuilder edifactData = new StringBuilder();
		try {			
			interchangeControlRef = TransportCommonUtility.genrateRandomNumber();
			messageNumber = TransportCommonUtility.generateControlNumber(despatchAdviceShipNoticeHeader.getMessageReference());
			transactionLogRepository.updateStControlId(transactionLog.getBpiLogId(), interchangeControlRef);
			edifactData.append(buildUNASegment())
			.append(buildUNBSegment(transactionLog))
			.append(buildUNHSegment())
			.append(buildBGMSegment(despatchAdviceShipNoticeHeader))
			.append(buildDTMSegment(despatchAdviceShipNoticeHeader))
			.append(buildDTM1Segment(despatchAdviceShipNoticeHeader))						
			.append(buildUNTSegment(despatchAdviceShipNoticeHeader))
			.append(buildUNZSegment());
			log.info(transactionLog.getBpiLogId() + " :: Completed Started EDI Convertion");
		} catch (Exception exception) {
			log.info(transactionLog.getBpiLogId() + " :: Got Exception In EDI Convertion :: "+exception.getMessage());
		}

		log.info("AperakOutboundXmlParser : generated output : " + edifactData);
		return edifactData.toString();
	}

	private StringBuilder buildUNASegment() {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNA:+.? '\n");
		return unaSegment;
	}

	private StringBuilder buildUNBSegment(TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNB")
		.append(ediDelimiter.getFieldDelimiter())
		.append("UNOB")
		.append(ediDelimiter.getFieldSeparator())
		.append("1")
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

	private StringBuilder buildUNHSegment() {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNH")
		.append(ediDelimiter.getFieldDelimiter())
		.append(messageNumber)
		.append(ediDelimiter.getFieldDelimiter())
		.append("IFTSTA")
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
	
	private StringBuilder buildBGMSegment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("BGM")
		.append(ediDelimiter.getFieldDelimiter())
		.append("784")
		.append(ediDelimiter.getFieldDelimiter())
		.append(getShipmentNo(despatchAdviceShipNoticeHeader,"BGM+220:04"))
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildDTMSegment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+137:")
		.append(TransportCommonUtility.convertToDateFormat(despatchAdviceShipNoticeHeader.getDocumentCreationDate(),DATEFORMAT.YYYYMMDDTHHMMSS.getAction(),DATEFORMAT.YYYYMMDD.getAction()))
		.append(ediDelimiter.getFieldSeparator())
		.append("102")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildDTM1Segment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+133:")
		.append(TransportCommonUtility.convertToDateFormat(despatchAdviceShipNoticeHeader.getDocumentCreationDate(),DATEFORMAT.YYYYMMDDTHHMMSS.getAction(),DATEFORMAT.YYYYMMDDHHMMSS.getAction()))
		.append(ediDelimiter.getFieldSeparator())
		.append("204")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
		
	}
	private StringBuilder buildREFSegment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("RFF+ADP")
		.append(ediDelimiter.getFieldSeparator())
		.append(getStatus(despatchAdviceShipNoticeHeader.getLstDesadvShipNoitceShipment().get(0).getStatus()))
		.append(ediDelimiter.getSegmentDelimiter())		
		.append("RFF+AAN")
		.append(ediDelimiter.getFieldSeparator())
		.append(getShipmentNo(despatchAdviceShipNoticeHeader,"RFF+ACD:02"))
		.append(ediDelimiter.getSegmentDelimiter())	
		.append("RFF+AHA")
		.append(ediDelimiter.getFieldSeparator())
		.append("")
		.append(ediDelimiter.getSegmentDelimiter())		
		.append("RFF+AHB")
		.append(ediDelimiter.getFieldSeparator())
		.append("ASUS")
		.append(ediDelimiter.getSegmentDelimiter());
		segmentCount+=4;
		return unaSegment;
	}

	private StringBuilder buildUNTSegment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNT")
		.append(ediDelimiter.getFieldDelimiter())
		.append(segmentCount)
		.append(ediDelimiter.getFieldDelimiter())
		.append(messageNumber)	
		.append(ediDelimiter.getSegmentDelimiter());
		return unaSegment;
	}
	
	private StringBuilder buildUNZSegment() {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNZ")
		.append(ediDelimiter.getFieldDelimiter())
		.append("1")
		.append(ediDelimiter.getFieldDelimiter())
		.append(interchangeControlRef)		
		.append(ediDelimiter.getSegmentDelimiter());
		return unaSegment;
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
}
