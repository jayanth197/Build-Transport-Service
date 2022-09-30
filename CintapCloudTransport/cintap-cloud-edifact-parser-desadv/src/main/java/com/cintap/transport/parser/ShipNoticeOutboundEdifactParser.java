package com.cintap.transport.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cintap.transport.common.enums.DATEFORMAT;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.common.EdiDelimiter;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItem;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCarton;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCartonItem;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipment;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipmentReference;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.model.PackLineItem;
import com.cintap.transport.repository.common.EdiDelimiterRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipNoticeOutboundEdifactParser {

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
			.append(buildRFFSegment(despatchAdviceShipNoticeHeader))
			.append(buildLineItemSegment(despatchAdviceShipNoticeHeader))			
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
		.append("DESADV")
		.append(ediDelimiter.getFieldSeparator())
		.append("D")
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
		.append("351")
		.append(ediDelimiter.getFieldDelimiter())
		.append(getShipmentNo(despatchAdviceShipNoticeHeader,"BGM+220:04"))
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildDTMSegment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+137")
		.append(ediDelimiter.getFieldSeparator())
		.append(TransportCommonUtility.convertToDateFormat(despatchAdviceShipNoticeHeader.getDocumentCreationDate(),DATEFORMAT.YYYYMMDDTHHMMSS.getAction(),DATEFORMAT.YYYYMMDD.getAction()))
		.append(ediDelimiter.getFieldSeparator())
		.append("102")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
	}
	
	private StringBuilder buildDTM1Segment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		unaSegment.append("DTM+133")
		.append(ediDelimiter.getFieldSeparator())
		.append(TransportCommonUtility.convertToDateFormat(despatchAdviceShipNoticeHeader.getDocumentCreationDate(),DATEFORMAT.YYYYMMDDTHHMMSS.getAction(),DATEFORMAT.YYYYMMDDHHMMSS.getAction()))
		.append(ediDelimiter.getFieldSeparator())
		.append("204")
		.append(ediDelimiter.getSegmentDelimiter());
		++segmentCount;
		return unaSegment;
		
	}
	private StringBuilder buildRFFSegment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
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
		
	private StringBuilder buildLineItemSegment(DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader) {
		StringBuilder unaSegment = new StringBuilder();
		Map<String,Integer> lineMapper=new HashMap<String,Integer>();
		List<PackLineItem> lstPackLineItem=new ArrayList<>();	
		for(DespatchAdviceShipNoticeLineItem lineItem:despatchAdviceShipNoticeHeader.getLstDesadvShipNoitceLineItem()) {
			for(DespatchAdviceShipNoticeLineItemCarton lstDesadvShipNoitceLineItemCarton:lineItem.getLstDesadvShipNoitceLineItemCarton()) {				
				for(DespatchAdviceShipNoticeLineItemCartonItem lstDesadvShipNoitceLineItemCartonItem:lstDesadvShipNoitceLineItemCarton.getLstDesadvShipNoitceLineItemCartonItem()) {
					String partNo=lstDesadvShipNoitceLineItemCartonItem.getPartNumber();
					Integer qnty=Integer.parseInt(lstDesadvShipNoitceLineItemCartonItem.getItemQuantity());	
					PackLineItem packLineItem=new PackLineItem();
					if(lineMapper.containsKey(partNo)) {						
						lineMapper.computeIfPresent(partNo,(k,v)->v+qnty);
					}else {
						lineMapper.put(partNo, qnty);
						packLineItem.setVendorPartNumber(partNo);
						packLineItem.setConditionCode(lstDesadvShipNoitceLineItemCarton.getCustomerConditionCode());
						packLineItem.setHubso(getShipmentNo(despatchAdviceShipNoticeHeader,"BGM+220:04"));
						lstPackLineItem.add(packLineItem);
					}					
				}
			}
		}
		
		int i=0;
		for (PackLineItem packLineItem : lstPackLineItem) {		
			unaSegment.append("CPS")
			.append(ediDelimiter.getFieldDelimiter())
			.append(++i)
			.append(ediDelimiter.getSegmentDelimiter())
			.append("LIN")
			.append(ediDelimiter.getFieldDelimiter())
			.append(i)
			.append(ediDelimiter.getFieldDelimiter())
			.append(ediDelimiter.getFieldDelimiter())
			.append(packLineItem.getVendorPartNumber())
			.append(ediDelimiter.getFieldSeparator())
			.append("VP")
			.append(ediDelimiter.getSegmentDelimiter())
			//QTY
			.append("QTY+131")
			.append(ediDelimiter.getFieldSeparator())
			.append(lineMapper.get(packLineItem.getVendorPartNumber()))
			.append(ediDelimiter.getSegmentDelimiter())
			//FTX
			.append("FTX+ALL+++STOCK")
			.append(ediDelimiter.getFieldSeparator())
			.append("A")
			.append(ediDelimiter.getSegmentDelimiter())
			//FTX
			.append("FTX+ALL+++CC")
			.append(ediDelimiter.getFieldSeparator())
			.append(packLineItem.getConditionCode())
			.append(ediDelimiter.getSegmentDelimiter())
			//REF
			.append("RFF+ON")
			.append(ediDelimiter.getFieldSeparator())
			.append(packLineItem.getHubso())
			.append(ediDelimiter.getSegmentDelimiter());					
			segmentCount+=6;
		}
		
//		for(DespatchAdviceShipNoticeLineItem lineItem:despatchAdviceShipNoticeHeader.getLstDesadvShipNoitceLineItem()) {
//			for(DespatchAdviceShipNoticeLineItemCarton lstDesadvShipNoitceLineItemCarton:lineItem.getLstDesadvShipNoitceLineItemCarton()) {				
//				for(DespatchAdviceShipNoticeLineItemCartonItem lstDesadvShipNoitceLineItemCartonItem:lstDesadvShipNoitceLineItemCarton.getLstDesadvShipNoitceLineItemCartonItem()) {					
//					unaSegment.append("LIN")
//					.append(ediDelimiter.getFieldDelimiter())
//					.append(lstDesadvShipNoitceLineItemCartonItem.getLineSequenceNumber())
//					.append(ediDelimiter.getFieldDelimiter())
//					.append(ediDelimiter.getFieldDelimiter())
//					.append(lstDesadvShipNoitceLineItemCartonItem.getPartNumber())
//					.append(":VP")
//					.append(ediDelimiter.getSegmentDelimiter())
//					//QTY
//					.append("QTY+131:")
//					.append(lstDesadvShipNoitceLineItemCartonItem.getItemQuantity())
//					.append(ediDelimiter.getSegmentDelimiter())
//					//FTX
//					.append("FTX+ALL+++STOCK:")
//					.append("A")
//					.append(ediDelimiter.getSegmentDelimiter())
//					//FTX
//					.append("FTX+ALL+++CC:")
//					.append(lstDesadvShipNoitceLineItemCarton.getCustomerConditionCode())
//					.append(ediDelimiter.getSegmentDelimiter())
//					//REF
//					.append("RFF+ON:")
//					.append(getShipmentNo(despatchAdviceShipNoticeHeader,"BGM+220:04"))
//					.append(ediDelimiter.getSegmentDelimiter());					
//					segmentCount+=5;
//				}	
//			}		
//		}		
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
