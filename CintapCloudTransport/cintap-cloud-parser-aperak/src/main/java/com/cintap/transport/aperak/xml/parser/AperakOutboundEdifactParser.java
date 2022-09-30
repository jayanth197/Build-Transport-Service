package com.cintap.transport.aperak.xml.parser;

import java.util.Optional;

import com.cintap.transport.aperak.model.AperakData;
import com.cintap.transport.common.enums.EDIFACTDOCUMENTTYPE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.EdiDelimiter;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.common.EdiDelimiterRepository;
import com.cintap.transport.repository.common.PartnerPlantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AperakOutboundEdifactParser {

	@Autowired
	EdiDelimiterRepository ediDelimiterRepository;
	
	@Autowired
	PartnerPlantRepository partnerPlantRepository;
	
	EdiDelimiter ediDelimiter = null;
	
	String interchangeControlRef = null;
	String messageNumber = null;
	int segCount = 0;
	
	public String generateOutbound(AperakData aperakData,TransactionLog transactionLog, FileUploadParams fileUploadParams) {
		log.info("AperakOutboundXmlParser : generateOutbound : " + aperakData);
		
		ediDelimiter = ediDelimiterRepository.findDelimiterByPartner(fileUploadParams.getSenderPartnerId(), fileUploadParams.getReceiverPartnerId(), "EDI", "EDIFACT");
		Optional<String> optPartnerPlants = partnerPlantRepository.findNameByPartnerIdAndPartnerCode(fileUploadParams.getSenderPartnerId(), aperakData.getRefSource());
		String plantCode = "";
		StringBuilder edifactData = new StringBuilder();
		if(optPartnerPlants.isPresent()) {
			plantCode = optPartnerPlants.get();
			try {
				
				interchangeControlRef = TransportCommonUtility.genrateRandomNumber();
				messageNumber = TransportCommonUtility.generateControlNumber(aperakData.getMessageReferenceNumber());
				edifactData.append(buildUNASegment(aperakData,transactionLog))
				.append(buildUNBSegment(aperakData,transactionLog, plantCode))
				.append(buildUNHSegment(aperakData,transactionLog))
				.append(buildBGMSegment(aperakData,transactionLog))
				.append(buildDTMSegment(aperakData,transactionLog))
				.append(buildDTM1Segment(aperakData,transactionLog))
				.append(buildFTXSegment(aperakData,transactionLog))
				.append(buildRFFSegment(aperakData,transactionLog))
				.append(buildUNTSegment(aperakData,transactionLog))
				.append(buildUNZSegment(aperakData,transactionLog));
				log.info(transactionLog.getBpiLogId() + " :: Completed Started EDI Convertion");
			} catch (Exception exception) {
				log.info(transactionLog.getBpiLogId() + " :: Got Exception In EDI Convertion :: "+exception.getMessage());
			}
		}
		log.info("AperakOutboundXmlParser : generated output : " + edifactData);
		return edifactData.toString();
	}


	private StringBuilder buildUNASegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNA:+.? '");
		segCount = 0;
		return unaSegment;
	}

	private StringBuilder buildUNBSegment(AperakData aperakData,TransactionLog transactionLog, String plantCode) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNB")
		.append(ediDelimiter.getFieldDelimiter())
		.append("UNOB")
		.append(ediDelimiter.getFieldSeparator())
		.append("1")
		.append(ediDelimiter.getFieldDelimiter())
		.append(plantCode)
		.append(ediDelimiter.getFieldSeparator())
		.append(transactionLog.getReceiverIsa())
		.append(ediDelimiter.getFieldDelimiter())
		.append(transactionLog.getSenderPartnerCode())
		.append(ediDelimiter.getFieldSeparator())
		.append(transactionLog.getSenderIsa())
		.append(ediDelimiter.getFieldDelimiter())
		.append(TransportCommonUtility.getDocumentCreationDateAndTime())
		.append(ediDelimiter.getFieldDelimiter())
		.append(interchangeControlRef)
		.append(ediDelimiter.getSegmentDelimiter());
		segCount++;
		return unaSegment;
	}

	private StringBuilder buildUNHSegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNH")
		.append(ediDelimiter.getFieldDelimiter())
		.append(messageNumber)
		.append(ediDelimiter.getFieldDelimiter())
		.append("APERAK")
		.append(ediDelimiter.getFieldSeparator())
		.append("D")
		.append(ediDelimiter.getFieldSeparator())
		.append("96A")
		.append(ediDelimiter.getFieldSeparator())
		.append("UN")
		.append(ediDelimiter.getSegmentDelimiter());
		segCount++;
		return unaSegment;
	}
	
	private StringBuilder buildBGMSegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("BGM")
		.append(ediDelimiter.getFieldDelimiter())
		.append("23")
		.append(ediDelimiter.getFieldSeparator())
		.append(ediDelimiter.getFieldSeparator())
		.append(ediDelimiter.getFieldSeparator())
		.append("PurchaseOrder".equals(aperakData.getAckType())?EDIFACTDOCUMENTTYPE.DESADV_ASN.getType():EDIFACTDOCUMENTTYPE.ORDERS.getType())
		.append(ediDelimiter.getFieldDelimiter())
		.append(aperakData.getReferenceNumberAck())
		.append(ediDelimiter.getFieldDelimiter())
		.append("9")
		.append(ediDelimiter.getFieldDelimiter())
		.append(getResponseCode(aperakData.getResponseCode()))
		.append(ediDelimiter.getSegmentDelimiter());
		segCount++;
		return unaSegment;
	}
	
	private StringBuilder buildDTMSegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+137")
		.append(ediDelimiter.getFieldSeparator())
		.append(TransportCommonUtility.getDocumentDate())
		.append(ediDelimiter.getFieldSeparator())
		.append("102")
		.append(ediDelimiter.getSegmentDelimiter());
		segCount++;
		return unaSegment;
	}
	
	private StringBuilder buildDTM1Segment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+334")
		.append(ediDelimiter.getFieldSeparator())
		.append(TransportCommonUtility.getDocumentChangeDateAndTime())
		.append(ediDelimiter.getFieldSeparator())
		.append("204")
		.append(ediDelimiter.getSegmentDelimiter());
		segCount++;
		return unaSegment;
	}
	
	private StringBuilder buildFTXSegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		if (!aperakData.getResponseReason().equals("")) {
			unaSegment.append("FTX+AAI+++")
			.append(aperakData.getResponseReason())
			.append(ediDelimiter.getSegmentDelimiter());
			segCount++;
		}
		return unaSegment;
	}
	
	private StringBuilder buildRFFSegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		if (!aperakData.getBgmReferenceNumber().equals("")) {
			unaSegment.append("RFF")
			.append(ediDelimiter.getFieldDelimiter())
			.append("ON")
			.append(ediDelimiter.getFieldSeparator())
			.append(aperakData.getBgmReferenceNumber())
			.append(ediDelimiter.getSegmentDelimiter());
			segCount++;
		}
		return unaSegment;
	}


	private StringBuilder buildUNTSegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNT")
		.append(ediDelimiter.getFieldDelimiter())
		.append(segCount)
		.append(ediDelimiter.getFieldDelimiter())
		.append(messageNumber)	
		.append(ediDelimiter.getSegmentDelimiter());
		return unaSegment;
	}
	
	private StringBuilder buildUNZSegment(AperakData aperakData,TransactionLog transactionLog) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("UNZ")
		.append(ediDelimiter.getFieldDelimiter())
		.append("1")
		.append(ediDelimiter.getFieldDelimiter())
		.append(interchangeControlRef)		
		.append(ediDelimiter.getSegmentDelimiter());
		return unaSegment;
	}	
	
	private String getResponseCode(String responseCode) {
		String value = "";
		switch (responseCode) {
			case "Accepted":
				value = "AP";
				break;
			case "Received":
				value = "RE";
				break;
			case "Rejected":
				value = "RJ";
				break;
			case "Error":
				value = "RJ";
				break;
			default:
				break;
		}
		return value;
	}
}
