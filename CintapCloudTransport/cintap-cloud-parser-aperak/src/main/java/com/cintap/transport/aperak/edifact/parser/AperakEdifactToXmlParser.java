package com.cintap.transport.aperak.edifact.parser;

import java.util.Optional;

import com.cintap.transport.aperak.model.AperakData;
import com.cintap.transport.common.enums.EDIFACTJSONFIELDS;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AperakEdifactToXmlParser {

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	AperakOutboundXmlParser aperakOutboundXmlParser;

	public FileUploadParams convertModelToEntity(String ediJson, FileUploadParams fileUploadParams) {
		log.info("AperakEdifactToXmlParser : convertModelToEntity : request edifact : " + ediJson);

		log.info("AperakEdifactToXmlParser : convertModelToEntity : JSON Object : ");
		AperakData aperakData = null;

		JSONObject obj = new JSONObject(ediJson);
		JSONArray interchangesArr = obj.getJSONArray(EDIFACTJSONFIELDS.INTERCHANGES.getAction());
		JSONObject interchangesObj = interchangesArr.getJSONObject(0);
		JSONArray trnsactionsArr = interchangesObj.getJSONArray(EDIFACTJSONFIELDS.TRANSACTIONS.getAction());

		JSONObject sender = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.SENDER.getAction());
		JSONObject receiver = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.RECEIVER.getAction());
		JSONObject dateTime = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.DATE_TIME.getAction());
		JSONObject transactionObj = trnsactionsArr.getJSONObject(0);
		JSONArray segmentArray = transactionObj.getJSONArray(EDIFACTJSONFIELDS.SEGMENTS.getAction());
		
		String identification = getIdentification(segmentArray);

		Optional<TransactionLog> optTransactionLog = transactionLogRepository.findByStpIdAndRtpIdAndGsControlId(
				fileUploadParams.getReceiverPartnerId(), fileUploadParams.getSenderPartnerId(), identification);

		if (optTransactionLog.isPresent()) {
			TransactionLog transactionLog = optTransactionLog.get();
			Integer bpiLogId = transactionLog.getBpiLogId();
			log.info("AperakEdifactToXmlParser : convertModelToEntity : Bpi Log Id : " + bpiLogId);

			fileUploadParams.setBpiLogId(bpiLogId);

			aperakData = AperakData.builder()
					.senderAddressId(checkPropertyAndGetValue(sender, EDIFACTJSONFIELDS.SENDER_IDENTIFICATION.getAction()))
					.senderAddressQual(checkPropertyAndGetValue(sender, EDIFACTJSONFIELDS.SENDER_QUALIFIER.getAction()))
					.receiverAddressId(checkPropertyAndGetValue(receiver, EDIFACTJSONFIELDS.RECEIVER_IDENTIFICATION.getAction()))
					.receiverAddressQual(checkPropertyAndGetValue(receiver, EDIFACTJSONFIELDS.RECEIVER_QUALIFIER.getAction()))
					.identification(identification)
					.documentCreationDate(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.DATE.getAction()))
					.documentCreationTime(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.TIME.getAction()))
					.messageReferenceNumber(checkPropertyAndGetValue(transactionObj, EDIFACTJSONFIELDS.MESSAGE_REFERENCE_NUMBER.getAction()))
					.build();
			
			aperakData = getAperakData(aperakData, segmentArray);

			fileUploadParams.setAckRefNum(aperakData.getReferenceNumberAck());
			fileUploadParams.setAckType(aperakData.getResponseCode());

			log.info("AperakEdifactToXmlParser : convertModelToEntity : parsed data : " + aperakData);

			String xmlData = aperakOutboundXmlParser.generateOutbound(aperakData, transactionLog, fileUploadParams);
			fileUploadParams.setOutboundRawFile(xmlData);
		} else {
			log.info("AperakEdifactToXmlParser : convertModelToEntity : no record found");
		}

		return fileUploadParams;
	}
	
	private AperakData getAperakData(AperakData aperakData, JSONArray segmentArray) {
		
		for (int i=0;i<segmentArray.length();i++) {
			JSONObject segmentObject = segmentArray.getJSONObject(i);
			if (segmentObject.has(EDIFACTJSONFIELDS.BGM.getAction())) {
				aperakData = buildBGM(segmentObject, aperakData);
			}
		}
							
		return aperakData;
	}
	
	private AperakData buildBGM(JSONObject elements, AperakData aperakData) {
		aperakData.setReferenceNumberAck(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.BGM_02.getAction()));
		aperakData.setResponseCode(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.BGM_03.getAction()));
		aperakData.setDocumentName(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.BGM_04.getAction()));
		return aperakData;
	}

	private String getIdentification(JSONArray segmentArray) {		
		String value = "";
		for (int i=0;i<segmentArray.length();i++) {
			JSONObject segmentObject = segmentArray.getJSONObject(i);
			if (segmentObject.has(EDIFACTJSONFIELDS.BGM.getAction())) {
				value = checkPropertyAndGetValue(segmentObject, EDIFACTJSONFIELDS.BGM_02.getAction());
				break;
			}
		}
		return value;
	}
	
	private String checkPropertyAndGetValue(JSONObject jsonObj, String key) {
		String value = "";
		if (jsonObj.has(key)) {
			value = jsonObj.getString(key);
		}
		return value;
	}
}
