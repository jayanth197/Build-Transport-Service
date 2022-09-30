package com.cintap.transport.orders.parser;

import java.time.Instant;

import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.EDIFACTJSONFIELDS;
import com.cintap.transport.common.enums.EDIFACTTRANSACTIONTYPE;
import com.cintap.transport.common.enums.STANDARD;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.entity.edifact.orders.OrdersAddress;
import com.cintap.transport.entity.edifact.orders.OrdersContactDetails;
import com.cintap.transport.entity.edifact.orders.OrdersFreeText;
import com.cintap.transport.entity.edifact.orders.OrdersFreeTextDetails;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;
import com.cintap.transport.entity.edifact.orders.OrdersHeaderMessage;
import com.cintap.transport.entity.edifact.orders.OrdersLineItem;
import com.cintap.transport.entity.edifact.orders.OrdersMessageDateTime;
import com.cintap.transport.entity.edifact.orders.OrdersReference;
import com.cintap.transport.entity.trans.TransactionLog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class OrdersInboundEdifactParser {

	public OrdersHeader convertModelToEntity(JSONObject interchangesObj, JSONObject transactionObj, FileUploadParams fileUploadParams) {
		
		OrdersHeader ordersHeader = OrdersHeader
				.builder()
				.senderPartnerId(fileUploadParams.getSenderPartnerId())
				.receiverPartnerId(fileUploadParams.getReceiverPartnerId())
				.build();
		
		// sender id and qualifier
		JSONObject sender = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.SENDER.getAction());
		ordersHeader.setSenderAddress(checkPropertyAndGetValue(sender, EDIFACTJSONFIELDS.SENDER_IDENTIFICATION.getAction()));
		ordersHeader.setSenderQualifier(checkPropertyAndGetValue(sender, EDIFACTJSONFIELDS.SENDER_QUALIFIER.getAction()));

		// receiver id and qualifier
		JSONObject receiver = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.RECEIVER.getAction());
		ordersHeader.setReceiverAddress(checkPropertyAndGetValue(receiver, EDIFACTJSONFIELDS.RECEIVER_IDENTIFICATION.getAction()));
		ordersHeader.setReceiverQualifier(checkPropertyAndGetValue(receiver, EDIFACTJSONFIELDS.RECEIVER_QUALIFIER.getAction()));

		// creation date and time
		JSONObject dateTime = interchangesObj.getJSONObject(EDIFACTJSONFIELDS.DATE_TIME.getAction());
		ordersHeader.setCreationDate(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.DATE.getAction()));
		ordersHeader.setCreationTime(checkPropertyAndGetValue(dateTime, EDIFACTJSONFIELDS.TIME.getAction()));

		// Interchange control ref
		ordersHeader.setInterchangeControlRef(checkPropertyAndGetValue(interchangesObj, EDIFACTJSONFIELDS.INTERCHANGE_CONTROL_REF.getAction()));

		// bgm
//		JSONArray trnsactionsArr = interchangesObj.getJSONArray("transactions");
//		for (int i = 0; i < trnsactionsArr.length(); i++) {
//			JSONObject transactionObj = trnsactionsArr.getJSONObject(i);
			JSONArray segmentArray = transactionObj.getJSONArray(EDIFACTJSONFIELDS.SEGMENTS.getAction());

			// header messages
			ordersHeader.addOrdersHeaderMessage(buildHeaderMessage(transactionObj));

			ordersHeader = segmentMapper(segmentArray, ordersHeader);
//		}
		
		return ordersHeader;
	}

	private OrdersHeaderMessage buildHeaderMessage(JSONObject transaction) {

		JSONObject messageIdentifier = (JSONObject) transaction.get(EDIFACTJSONFIELDS.MESSAGE_IDENTIFIER.getAction());
		return OrdersHeaderMessage.builder()
				.agency(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.AGENCY.getAction()))
				.docType(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.MESSAGE_TYPE.getAction()))
				.release(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.RELEASE.getAction()))
				.version(checkPropertyAndGetValue(messageIdentifier, EDIFACTJSONFIELDS.VERSION.getAction()))
				.messageNumber(checkPropertyAndGetValue(transaction, EDIFACTJSONFIELDS.MESSAGE_REFERENCE_NUMBER.getAction()))
				.build();
	}

	private OrdersHeader segmentMapper(JSONArray segmentArray, OrdersHeader ordersHeader) {

		for(int i=0; i< segmentArray.length();i++)
		{
			JSONObject segmentObject = segmentArray.getJSONObject(i);
			if (segmentObject.has(EDIFACTJSONFIELDS.BGM.getAction())) {
				JSONObject bgm01 = segmentObject.getJSONObject(EDIFACTJSONFIELDS.BGM_01.getAction());
				ordersHeader.setBgmIdentifierOrderChange(checkPropertyAndGetValue(bgm01, EDIFACTJSONFIELDS.BGM_01_02.getAction()));
				ordersHeader.setBgmOrder(checkPropertyAndGetValue(segmentObject, EDIFACTJSONFIELDS.BGM_02.getAction()));

			} else if (segmentObject.has(EDIFACTJSONFIELDS.DTM.getAction())) {
				OrdersMessageDateTime ordersMessageDateTime = buildMessageDateTime(segmentObject);
				ordersHeader.addOrdersMessageDateTime(ordersMessageDateTime);
			} else if (segmentObject.has(EDIFACTJSONFIELDS.FTX.getAction())) {
				OrdersFreeText ordersFreeText = buildFreeText(segmentObject);
				ordersHeader.addOrdersFreeText(ordersFreeText);
			} else if (segmentObject.has(EDIFACTJSONFIELDS.RFF_LOOP.getAction())) {
				JSONArray rffLoop = segmentObject.getJSONArray(EDIFACTJSONFIELDS.RFF_LOOP.getAction());
				JSONObject rffObject = rffLoop.getJSONObject(0);
				OrdersReference ordersReference = buildReferenceREF(rffObject);
				ordersHeader.addOrdersReference(ordersReference);
			} else if (segmentObject.has(EDIFACTJSONFIELDS.NAD_LOOP.getAction())) {
				JSONArray nadLoop = segmentObject.getJSONArray(EDIFACTJSONFIELDS.NAD_LOOP.getAction());
				OrdersAddress ordersAddress = buildAddress(nadLoop);
				ordersHeader.addOrdersAddress(ordersAddress);
			} else if (segmentObject.has(EDIFACTJSONFIELDS.CUX_LOOP.getAction())) {
				JSONArray cuxLoop = segmentObject.getJSONArray(EDIFACTJSONFIELDS.CUX_LOOP.getAction());
				JSONObject cuxObject = cuxLoop.getJSONObject(0);
				ordersHeader.setCurrencyCode(buildCurrencyCode(cuxObject));
			} else if (segmentObject.has(EDIFACTJSONFIELDS.LIN_LOOP.getAction())) {
				JSONArray linLoop = segmentObject.getJSONArray(EDIFACTJSONFIELDS.LIN_LOOP.getAction());
				ordersHeader.addOrdersLineItem(buildLineItems(linLoop));
			}
		}
			
		return ordersHeader;
	}

	private OrdersMessageDateTime buildMessageDateTime(JSONObject elements) {
		JSONObject dtm = elements.getJSONObject(EDIFACTJSONFIELDS.DTM_01.getAction());
		return OrdersMessageDateTime.builder()
				.qualifier(checkPropertyAndGetValue(dtm, EDIFACTJSONFIELDS.DTM_01_01.getAction()))
				.date(checkPropertyAndGetValue(dtm, EDIFACTJSONFIELDS.DTM_01_02.getAction()))
				.dateFormat(checkPropertyAndGetValue(dtm, EDIFACTJSONFIELDS.DTM_01_03.getAction()))
				.build();
	}

	private OrdersFreeText buildFreeText(JSONObject elements) {
		String qualifier = checkPropertyAndGetValue(elements, EDIFACTJSONFIELDS.FTX_01.getAction());
		OrdersFreeText ordersFreeText = OrdersFreeText
				.builder()
				.qualifier(qualifier)
				.build();
		if (elements.has(EDIFACTJSONFIELDS.FTX_04.getAction())) {
			Object obj = elements.get(EDIFACTJSONFIELDS.FTX_04.getAction());
			OrdersFreeTextDetails ordersFreeTextDetails = new OrdersFreeTextDetails();
			if (EDIFACTJSONFIELDS.SUR.getAction().equals(qualifier)) {
				String content = "";
				if (obj instanceof JSONObject) {
					JSONObject ftx04 = (JSONObject) obj;
					content = checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_01.getAction()) + "/"
							+ checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_02.getAction()) + "/"
							+ checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_03.getAction()) + "/"
							+ checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_04.getAction()) + "/"
							+ checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_05.getAction());
				} else {
					content = (String) obj;
				}
				ordersFreeTextDetails.setContent(content);
			} else {
				if (obj instanceof JSONObject) {
					JSONObject ftx04 = (JSONObject) obj;
					ordersFreeTextDetails
							.setSequence(checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_01.getAction()));
					ordersFreeTextDetails
							.setContent(checkPropertyAndGetValue(ftx04, EDIFACTJSONFIELDS.FTX_04_02.getAction()));
				} else {
					String sequence = (String) obj;
					ordersFreeTextDetails.setSequence(sequence);
				}
			}
			ordersFreeText.addOrdersFreeTextDetails(ordersFreeTextDetails);
		}
		return ordersFreeText;
	}

	private String buildCurrencyCode(JSONObject currencyObject) {
		JSONObject cux01 = currencyObject.getJSONObject(EDIFACTJSONFIELDS.CUX_01.getAction());
		return checkPropertyAndGetValue(cux01, EDIFACTJSONFIELDS.CUX_01_02.getAction());
	}


	private OrdersReference buildReferenceREF(JSONObject rff) {
		Object rffObject = rff.get(EDIFACTJSONFIELDS.RFF_01.getAction());

		if (rffObject instanceof JSONObject) {
			JSONObject rff01 = (JSONObject) rffObject;
			return OrdersReference.builder().qualifier(checkPropertyAndGetValue(rff01, EDIFACTJSONFIELDS.RFF_01_01.getAction()))
					.identifier(checkPropertyAndGetValue(rff01, EDIFACTJSONFIELDS.RFF_01_02.getAction())).build();
		} else {
			String qualifier = (String) rffObject;
			return OrdersReference.builder().qualifier(qualifier).build();
		}
	}

	private OrdersLineItem buildLineItems(JSONArray linLoop) {
		OrdersLineItem ordersLineItem = OrdersLineItem.builder().build();
		
		for (int i = 0; i < linLoop.length(); i++) {
			JSONObject linObject = linLoop.getJSONObject(i);
			if (linObject.has(EDIFACTJSONFIELDS.LIN.getAction())) {
				ordersLineItem = buildLineItem(ordersLineItem, linObject);
			} else if (linObject.has(EDIFACTJSONFIELDS.PIA.getAction())) {
				ordersLineItem.setAdditionalPartNumber(checkPropertyAndGetValue(linObject, EDIFACTJSONFIELDS.PIA_02.getAction()));
			} else if (linObject.has(EDIFACTJSONFIELDS.IMD.getAction())) {
				JSONObject imd03 = linObject.getJSONObject(EDIFACTJSONFIELDS.IMD_03.getAction());
				ordersLineItem.setSupplierPartDescription(checkPropertyAndGetValue(imd03, EDIFACTJSONFIELDS.IMD_03_04.getAction()));
			} else if (linObject.has(EDIFACTJSONFIELDS.QTY.getAction())) {
				JSONObject qty01 = linObject.getJSONObject(EDIFACTJSONFIELDS.QTY_01.getAction());
				ordersLineItem.setQuantity(checkPropertyAndGetValue(qty01, EDIFACTJSONFIELDS.QTY_01_02.getAction()));
			} else if (linObject.has(EDIFACTJSONFIELDS.FTX.getAction())) {
				Object ftx04 = linObject.get(EDIFACTJSONFIELDS.FTX_04.getAction());
				if (ftx04 instanceof JSONObject) {
					JSONObject ftxObject = (JSONObject) ftx04;
					String type = checkPropertyAndGetValue(ftxObject, EDIFACTJSONFIELDS.FTX_04_01.getAction());
					String value = checkPropertyAndGetValue(ftxObject, EDIFACTJSONFIELDS.FTX_04_02.getAction());
					if (type.equals(EDIFACTJSONFIELDS.CC.getAction()))
						ordersLineItem.setFreeTextConditionCode(value);
					else
						ordersLineItem.setFreeTextStockFile(value);
				}
			} else if (linObject.has(EDIFACTJSONFIELDS.PRI_LOOP.getAction())) {
				JSONArray priLoop = linObject.getJSONArray(EDIFACTJSONFIELDS.PRI_LOOP.getAction());
				JSONObject priObject = priLoop.getJSONObject(0);
				ordersLineItem.setUnitPrice(checkPropertyAndGetValue(priObject, EDIFACTJSONFIELDS.PRI_01_02.getAction()));
			}
		}
		
		return ordersLineItem;
	}

	private OrdersLineItem buildLineItem(OrdersLineItem ordersLineItem, JSONObject linObject) {
		JSONObject lin03 = linObject.getJSONObject(EDIFACTJSONFIELDS.LIN_03.getAction());
		ordersLineItem.setItemLineNumber(checkPropertyAndGetValue(linObject, EDIFACTJSONFIELDS.LIN_01.getAction()));
		ordersLineItem.setAsusPartNumber(checkPropertyAndGetValue(lin03, EDIFACTJSONFIELDS.LIN_03_01.getAction()));
		return ordersLineItem;
	}

	public TransactionLog buildTransactionLog(FileUploadParams fileUploadParams, OrdersHeader ordersHeader) {
		TransactionLog transactionLog;

		transactionLog = TransactionLog.builder()
				.batchId(fileUploadParams.getBatchId()).processType(TRANSACTIONSOURCE.ELECTRONIC.getValue())
				.senderIsa(fileUploadParams.getSenderIsaId()).receiverIsa(fileUploadParams.getReceiverIsaId())
				.stpTransId(ordersHeader.getBgmOrder())
				.stpSourceId("" + EDIFACTTRANSACTIONTYPE.ORDERS.getValue()).stpId(fileUploadParams.getSenderPartnerId())
				.rtpId(fileUploadParams.getReceiverPartnerId()).ediVersion(fileUploadParams.getEdiVersion())
				.stControlNumber(ordersHeader.getInterchangeControlRef())
				.transactionType(
						null != fileUploadParams.getTrnType() ? fileUploadParams.getTrnType().toUpperCase() : "")
				.fileType(fileUploadParams.getActualFileType()).batchId(fileUploadParams.getBatchId())
				.partnerProcessDate(ordersHeader.getCreationDate()).createdDate(Instant.now().toString())
				.createdBy(fileUploadParams.getPartnerId()).statusId(CINTAPBPISTATUS.NEW.getStatusId())
				.source(fileUploadParams.getSource())
				.standard(STANDARD.EDIFACT.getStandard()).build();

		return transactionLog;
	}

	private OrdersAddress buildAddress(JSONArray nadLoop) {
		
		JSONObject nadObject = nadLoop.getJSONObject(0);

		OrdersAddress ordersAddress = OrdersAddress
				.builder()
				.type(checkPropertyAndGetValue(nadObject, EDIFACTJSONFIELDS.NAD_01.getAction()))
				.identifier(checkPropertyAndGetValue(nadObject, EDIFACTJSONFIELDS.NAD_02.getAction()))
				.name(checkPropertyAndGetValue(nadObject, EDIFACTJSONFIELDS.NAD_04.getAction()))
				.build();
		
		if (nadObject.has(EDIFACTJSONFIELDS.NAD_03.getAction())) {
			JSONObject nad03 = nadObject.getJSONObject(EDIFACTJSONFIELDS.NAD_03.getAction());
			ordersAddress.setAddress1(checkPropertyAndGetValue(nad03, EDIFACTJSONFIELDS.NAD_03_01.getAction()));
			ordersAddress.setAddress2(checkPropertyAndGetValue(nad03, EDIFACTJSONFIELDS.NAD_03_02.getAction()));
			ordersAddress.setAddress3(checkPropertyAndGetValue(nad03, EDIFACTJSONFIELDS.NAD_03_03.getAction()));
			ordersAddress.setAddress4(checkPropertyAndGetValue(nad03, EDIFACTJSONFIELDS.NAD_03_04.getAction()));
			ordersAddress.setAddress5(checkPropertyAndGetValue(nad03, EDIFACTJSONFIELDS.NAD_03_05.getAction()));
		}

		if (nadLoop.length() == 2) {
			JSONObject locObject = nadLoop.getJSONObject(1);
			if (EDIFACTJSONFIELDS.FW.getAction().equals(ordersAddress.getType())) {
				JSONArray ctaArray = locObject.getJSONArray(EDIFACTJSONFIELDS.CTA_LOOP.getAction());
				ordersAddress.addOrdersContactDetails(buildContactDetails(ctaArray));
			} else {
				JSONObject loc04 = locObject.getJSONObject(EDIFACTJSONFIELDS.LOC_04.getAction());
				ordersAddress.setCountryCode(checkPropertyAndGetValue(loc04, EDIFACTJSONFIELDS.LOC_04_04.getAction()));
			}
		}
		
		return ordersAddress;
	}

	private OrdersContactDetails buildContactDetails(JSONArray ctaArray) {
		OrdersContactDetails ordersContactDetails = OrdersContactDetails
				.builder()
				.build();
		for (int i = 0; i < ctaArray.length(); i++) {
			JSONObject ctaObject = ctaArray.getJSONObject(i);
			if (i == 0) {
				ordersContactDetails.setContactName(checkPropertyAndGetValue(ctaObject, EDIFACTJSONFIELDS.CTA_02.getAction()));
			} else {
				JSONObject comObject = ctaObject.getJSONObject(EDIFACTJSONFIELDS.COM_01.getAction());
				String value = checkPropertyAndGetValue(comObject, EDIFACTJSONFIELDS.COM_01_01.getAction());
				String type = checkPropertyAndGetValue(comObject, EDIFACTJSONFIELDS.COM_01_02.getAction());
				if (type.equals(EDIFACTJSONFIELDS.TE.getAction()))
					ordersContactDetails.setTelephoneNumber(value);
				else
					ordersContactDetails.setFaxNumber(value);
			}
		}
		return ordersContactDetails;
	}
	
	private String checkPropertyAndGetValue(JSONObject jsonObj, String key) {
		String value = "";
		if (jsonObj.has(key)) {
			value = jsonObj.getString(key);
		}
		return value;
	}

}
