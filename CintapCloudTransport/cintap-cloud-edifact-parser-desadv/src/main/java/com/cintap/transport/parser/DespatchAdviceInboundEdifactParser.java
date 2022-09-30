package com.cintap.transport.parser;

import java.util.ArrayList;
import java.util.List;

import com.cintap.transport.common.enums.EDIFACTJSONFIELDS;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceAddress;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvFreeText;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvFreeTextDetails;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvHeaderMessage;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvInfo;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvLineItem;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvMessageDateTime;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvReference;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvSummary;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvTransportInformation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DespatchAdviceInboundEdifactParser {
	
	public DespatchAdviceHeader convertModelToEntity(JSONObject interchangesObj, JSONObject transactionObj, FileUploadParams fileUploadParams) {
		
		log.info("DespatchAdviceInboundEdifactParser : Received Request for converting to Entity");
		
		DespatchAdviceHeader edifactDesadvHeader = DespatchAdviceHeader
				.builder()
				.senderPartnerId(fileUploadParams.getSenderPartnerId())
				.receiverPartnerId(fileUploadParams.getReceiverPartnerId())
				.build();
				
		// sender id and qualifier
		JSONObject sender = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.SENDER.getAction());
		edifactDesadvHeader.setSenderAddress(checkPropertyAndGetValue(sender, EDIFACTJSONFIELDS.SENDER_IDENTIFICATION.getAction()));
		edifactDesadvHeader.setSenderQualifier(checkPropertyAndGetValue(sender, EDIFACTJSONFIELDS.SENDER_QUALIFIER.getAction()));

		// receiver id and qualifier
		JSONObject receiver = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.RECEIVER.getAction());
		edifactDesadvHeader.setReceiverAddress(checkPropertyAndGetValue(receiver, EDIFACTJSONFIELDS.RECEIVER_IDENTIFICATION.getAction()));
		edifactDesadvHeader.setReceiverQualifier(checkPropertyAndGetValue(receiver, EDIFACTJSONFIELDS.RECEIVER_QUALIFIER.getAction()));

		// creation date and time
		JSONObject dateTime = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.DATE_TIME.getAction());
		edifactDesadvHeader.setCreationDate(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.DATE.getAction()));
		edifactDesadvHeader.setCreationTime(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.TIME.getAction()));

		// Interchange control ref
		edifactDesadvHeader.setInterchangeControlRef(checkPropertyAndGetValue(interchangesObj, EDIFACTJSONFIELDS.INTERCHANGE_CONTROL_REF.getAction()));

		// bgm
//		JSONArray trnsactionsArr = interchangesObj.getJSONArray("transactions");
//		for (int i = 0; i < trnsactionsArr.length(); i++) {
//			JSONObject transactionObj = trnsactionsArr.getJSONObject(i);
			JSONArray segmentArray = transactionObj.getJSONArray(EDIFACTJSONFIELDS.SEGMENTS.getAction());

			// header messages
			edifactDesadvHeader.addEdifactDesadvHeaderMessage(buildHeaderMessage(transactionObj));

			edifactDesadvHeader = segmentMapper(segmentArray, edifactDesadvHeader);
//		}
		
		log.info("DespatchAdviceInboundEdifactParser : Convert Model class ###  "+TransportCommonUtility.convertObjectToJson(edifactDesadvHeader));
		
		return edifactDesadvHeader;
	}

	private DespatchAdviceHeader segmentMapper(JSONArray segmentArray, DespatchAdviceHeader edifactDesadvHeader) {

		List<EdifactDesadvLineItem> lstDesadvLineItems = new ArrayList<EdifactDesadvLineItem>();
		List<EdifactDesadvReference> lstDesadvReferences = new ArrayList<EdifactDesadvReference>();
		String rffSeg = "CPS";
		
		for (int j = 0; j < segmentArray.length(); j++) {
			JSONObject segmentObject = segmentArray.getJSONObject(j);
			if(segmentObject.has(EDIFACTJSONFIELDS.BGM.getAction()))
			{
				edifactDesadvHeader.setBgmDocNameCode(checkPropertyAndGetValue(segmentObject, EDIFACTJSONFIELDS.BGM_01.getAction()));
				edifactDesadvHeader.setBgmDocMsgNumber(checkPropertyAndGetValue(segmentObject, EDIFACTJSONFIELDS.BGM_02.getAction()));
				edifactDesadvHeader.setBgmVersion(checkPropertyAndGetValue(segmentObject, EDIFACTJSONFIELDS.BGM_03.getAction()));
			}else if(segmentObject.has(EDIFACTJSONFIELDS.DTM.getAction()))
			{
				EdifactDesadvMessageDateTime edifactDesadvMessageDateTime = buildMessageDateTime(segmentObject);
				edifactDesadvHeader.addEdifactDesadvMessageDateTime(edifactDesadvMessageDateTime);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.MOA.getAction()))
			{
				EdifactDesadvSummary edifactDesadvSummary = buildSummary(segmentObject);
				edifactDesadvHeader.addEdifactDesadvSummary(edifactDesadvSummary);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.NAD_01.getAction()))
			{
				DespatchAdviceAddress edifactDesadvAddress = buildAddress(segmentObject);
				edifactDesadvHeader.addEdifactDesadvAddress(edifactDesadvAddress);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.TDT.getAction()))
			{
				EdifactDesadvTransportInformation edifactDesadvTransportInformation = buildTransportInformation(segmentObject);
				edifactDesadvHeader.addEdifactDesadvTransportInformation(edifactDesadvTransportInformation);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.LIN.getAction()))
			{
				rffSeg = EDIFACTJSONFIELDS.LIN.getAction();
				EdifactDesadvLineItem edifactDesadvLineItem = buildLineItem(segmentObject);
				lstDesadvLineItems.add(edifactDesadvLineItem);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.PIA.getAction()))
			{
				int size = lstDesadvLineItems.size();
				EdifactDesadvLineItem edifactDesadvLineItem = lstDesadvLineItems.get(size-1);
				edifactDesadvLineItem = buildLineItemPIA(edifactDesadvLineItem, segmentObject);
				lstDesadvLineItems.set(size-1,edifactDesadvLineItem);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.IMD.getAction()))
			{
				int size = lstDesadvLineItems.size();
				EdifactDesadvLineItem edifactDesadvLineItem = lstDesadvLineItems.get(size-1);
				edifactDesadvLineItem = buildLineItemIMD(edifactDesadvLineItem, segmentObject);
				lstDesadvLineItems.set(size-1,edifactDesadvLineItem);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.QTY.getAction()))
			{
				int size = lstDesadvLineItems.size();
				EdifactDesadvLineItem edifactDesadvLineItem = lstDesadvLineItems.get(size-1);
				edifactDesadvLineItem = buildLineItemQTY(edifactDesadvLineItem, segmentObject);
				lstDesadvLineItems.set(size-1,edifactDesadvLineItem);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.PCI.getAction()))
			{
				EdifactDesadvReference edifactDesadvReference = buildReferencePCI(segmentObject);
				lstDesadvReferences.add(edifactDesadvReference);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.RFF.getAction()))
			{
				int size = lstDesadvReferences.size();
				EdifactDesadvReference edifactDesadvReference = lstDesadvReferences.get(size-1);
				edifactDesadvReference = buildReferenceREF(edifactDesadvReference, segmentObject);
				lstDesadvReferences.set(size-1 ,edifactDesadvReference);
			}else if(segmentObject.has(EDIFACTJSONFIELDS.FTX.getAction()))
			{
				if (EDIFACTJSONFIELDS.LIN.getAction().equals(rffSeg)) {
					int size = lstDesadvLineItems.size();
					EdifactDesadvLineItem edifactDesadvLineItem = lstDesadvLineItems.get(size-1);
					edifactDesadvLineItem = buildLineItemFTX(edifactDesadvLineItem, segmentObject);
					lstDesadvLineItems.set(size-1,edifactDesadvLineItem);
				} else {
					EdifactDesadvFreeText edifactDesadvFreeText = buildFreeText(segmentObject);
					edifactDesadvHeader.addEdifactDesadvFreeText(edifactDesadvFreeText);
				}
			}else if(segmentObject.has(EDIFACTJSONFIELDS.CNT.getAction()))
			{
				EdifactDesadvInfo edifactDesadvInfo = buildInfoCNT(segmentObject);
				edifactDesadvHeader.addEdifactDesadvInfo(edifactDesadvInfo);
			}
		}
		
		for(EdifactDesadvReference reference: lstDesadvReferences) {
			edifactDesadvHeader.addEdifactDesadvReference(reference);
		}
		for(EdifactDesadvLineItem lineItem: lstDesadvLineItems) {
			edifactDesadvHeader.addEdifactDesadvLineItem(lineItem);
		}
		
		return edifactDesadvHeader;
	}

	private EdifactDesadvHeaderMessage buildHeaderMessage(JSONObject transaction) {

		JSONObject messageIdentifier = (JSONObject) transaction.get(EDIFACTJSONFIELDS.MESSAGE_IDENTIFIER.getAction());
		return EdifactDesadvHeaderMessage.builder()
				.agency(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.AGENCY.getAction()))
				.docType(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.MESSAGE_TYPE.getAction()))
				.release(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.RELEASE.getAction()))
				.version(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.VERSION.getAction()))
				.messageNumber(checkPropertyAndGetValue(transaction, EDIFACTJSONFIELDS.MESSAGE_REFERENCE_NUMBER.getAction()))
				.build();
	}

	private EdifactDesadvTransportInformation buildTransportInformation(JSONObject elements) {

		JSONObject transportObject = elements.getJSONObject(EDIFACTJSONFIELDS.TDT_04.getAction());
		return EdifactDesadvTransportInformation
				.builder()
				.qualifier(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.TDT_01.getAction()))
				.shipType(checkPropertyAndGetValue(transportObject, EDIFACTJSONFIELDS.TDT_04_01.getAction()))
				.shipTypeDescription(checkPropertyAndGetValue(transportObject, EDIFACTJSONFIELDS.TDT_04_02.getAction()))
				.build();
	}

	private EdifactDesadvSummary buildSummary(JSONObject elements) {
		
		JSONObject summary = elements.getJSONObject(EDIFACTJSONFIELDS.MOA_01.getAction());
		return EdifactDesadvSummary
				.builder()
				.qualifier(checkPropertyAndGetValue(summary, EDIFACTJSONFIELDS.MOA_01_01.getAction()))
				.invoiceAmount(checkPropertyAndGetValue(summary, EDIFACTJSONFIELDS.MOA_01_02.getAction()))
				.currency(checkPropertyAndGetValue(summary, EDIFACTJSONFIELDS.MOA_01_03.getAction()))
				.build();
	}

	private EdifactDesadvMessageDateTime buildMessageDateTime(JSONObject elements) {

		JSONObject dateTime = elements.getJSONObject(EDIFACTJSONFIELDS.DTM_01.getAction());
		return EdifactDesadvMessageDateTime
				.builder()
				.qualifier(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.DTM_01_01.getAction()))
				.date(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.DTM_01_02.getAction()))
				.dateFormat(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.DTM_01_03.getAction()))
				.build();
	}
	
	private EdifactDesadvFreeText buildFreeText(JSONObject elements) {
		EdifactDesadvFreeText edifactDesadvFreeText = EdifactDesadvFreeText
				.builder()
				.qualifier(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.FTX_01.getAction()))
				.build();
		Object obj = elements.get(EDIFACTJSONFIELDS.FTX_04.getAction());
		EdifactDesadvFreeTextDetails edifactDesadvFreeTextDetails = new EdifactDesadvFreeTextDetails();
		if(obj instanceof JSONObject) {
			JSONObject ftx04 = (JSONObject) obj;
			edifactDesadvFreeTextDetails.setSequence(checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_01.getAction()));
			edifactDesadvFreeTextDetails.setContent(checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_02.getAction()));	
		}else {
			String sequence = (String) obj;
			edifactDesadvFreeTextDetails.setSequence(sequence);
		}
		edifactDesadvFreeText.addEdifactDesadvFreeTextDetails(edifactDesadvFreeTextDetails);
		return edifactDesadvFreeText;
	}

	private EdifactDesadvLineItem buildLineItemFTX(EdifactDesadvLineItem edifactDesadvLineItem, JSONObject elements) {
		edifactDesadvLineItem.setFreeText(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.FTX_04.getAction()));
		return edifactDesadvLineItem;
	}

	private EdifactDesadvReference buildReferenceREF(EdifactDesadvReference edifactDesadvReference, JSONObject elements) {
		Object rff01 = elements.get(EDIFACTJSONFIELDS.RFF_01.getAction());
		if(rff01 instanceof JSONObject) {
			JSONObject rff01Object = (JSONObject) rff01;
			edifactDesadvReference.setQualifier(checkPropertyAndGetValue(rff01Object, EDIFACTJSONFIELDS.RFF_01_01.getAction()));
			edifactDesadvReference.setIdentifier(checkPropertyAndGetValue(rff01Object, EDIFACTJSONFIELDS.RFF_01_02.getAction()));
		}else {
			String qualifier = (String) rff01;
			edifactDesadvReference.setQualifier(qualifier);
		}
		return edifactDesadvReference;
	}

	private EdifactDesadvReference buildReferencePCI(JSONObject elements) {
		return EdifactDesadvReference
				.builder()
				.packageIdentificationCode(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.PCI_01.getAction()))
				.build();
	}

	private EdifactDesadvLineItem buildLineItemQTY(EdifactDesadvLineItem edifactDesadvLineItem, JSONObject elements) {
		JSONObject qty01 = elements.getJSONObject(EDIFACTJSONFIELDS.QTY_01.getAction());
		String type = checkPropertyAndGetValue(qty01, EDIFACTJSONFIELDS.QTY_01_01.getAction());
		String value = checkPropertyAndGetValue(qty01, EDIFACTJSONFIELDS.QTY_01_02.getAction());
		if (type.equals("12"))
			edifactDesadvLineItem.setQuantity(value);
		else if (type.equals("21"))
			edifactDesadvLineItem.setTotalPalletCount(value);
		else if (type.equals("22"))
			edifactDesadvLineItem.setTotalCartonCount(value);
		else
			edifactDesadvLineItem.setUnitPrice(value);

		return edifactDesadvLineItem;
	}

	private EdifactDesadvLineItem buildLineItemIMD(EdifactDesadvLineItem edifactDesadvLineItem, JSONObject elements) {
		Object imd03 = elements.get(EDIFACTJSONFIELDS.IMD_03.getAction());
		if (imd03 instanceof JSONObject) {
			JSONObject imd03Object = (JSONObject) imd03;
			String type = checkPropertyAndGetValue(imd03Object, EDIFACTJSONFIELDS.IMD_03_01.getAction());
			String value = checkPropertyAndGetValue(imd03Object, EDIFACTJSONFIELDS.IMD_03_04.getAction());
			value = value.replaceAll("<", " ").replaceAll(">", " ");
			if (EDIFACTJSONFIELDS.VP.getAction().equals(type))
				edifactDesadvLineItem.setVendorPartDescription(value);
			else
				edifactDesadvLineItem.setSupplierPartDescription(value);
		}

		return edifactDesadvLineItem;
	}

	private EdifactDesadvInfo buildInfoCNT(JSONObject elements) {
		JSONObject cnt = elements.getJSONObject(EDIFACTJSONFIELDS.CNT_01.getAction());
		return EdifactDesadvInfo
				.builder()
				.numberOfLineItems(checkPropertyAndGetValue(cnt, EDIFACTJSONFIELDS.CNT_01_02.getAction()))
				.build();
	}

	private EdifactDesadvLineItem buildLineItemPIA(EdifactDesadvLineItem edifactDesadvLineItem, JSONObject elements) {
		JSONObject pia02 = elements.getJSONObject(EDIFACTJSONFIELDS.PIA_02.getAction());

		edifactDesadvLineItem.setBuyerPartNumber(checkPropertyAndGetValue(pia02, EDIFACTJSONFIELDS.PIA_02_01.getAction()));

		return edifactDesadvLineItem;
	}

	private EdifactDesadvLineItem buildLineItem(JSONObject elements) {
		JSONObject lineObject = elements.getJSONObject(EDIFACTJSONFIELDS.LIN_03.getAction());
		return EdifactDesadvLineItem
				.builder()
				.itemLineNumber(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.LIN_01.getAction()))
				.vendorPartNumber(checkPropertyAndGetValue(lineObject, EDIFACTJSONFIELDS.LIN_03_01.getAction()))
				.build();
	}

	private DespatchAdviceAddress buildAddress(JSONObject elements) {
		return  DespatchAdviceAddress.builder()
				.type(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.NAD_01.getAction()))
				.identifier(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.NAD_02.getAction()))
				.name(checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.NAD_04.getAction()))
				.build();
	}
	
	private String checkPropertyAndGetValue(JSONObject jsonObj, String key) {
		String value = "";
		if (jsonObj.has(key)) {
			value = jsonObj.getString(key);
		}
		return value;
	}
	
//	private String getSegmentFieldValue(String fieldId, Object elements) {
//		ArrayList<Map> lst = (ArrayList<Map>) elements;
//		for (Map at7 : lst) {
//			if (at7.get("Id").toString().equals(fieldId)) {
//				if (at7.get(EDIFACTCONSTANTS.CONTENT.getValue()) != null) {
//					return at7.get(EDIFACTCONSTANTS.CONTENT.getValue()).toString();
//				}
//				return "";
//			}
//		}
//		return "";
//	}
//
//	private String getSubelementFieldValue(String fieldId, Object elements) {
//		ArrayList<Map> lst = (ArrayList<Map>) elements;
//		for (Map at7 : lst) {
//			if (at7.get("Sequence").toString().equals(fieldId)) {
//				if (at7.get(EDIFACTCONSTANTS.CONTENT.getValue()) != null) {
//					return at7.get(EDIFACTCONSTANTS.CONTENT.getValue()).toString();
//				}
//				return "";
//			}
//		}
//		return "";
//	}
	
}
