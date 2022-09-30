package com.cintap.transport.orders.xml.parser;

import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cintap.transport.common.ftl.util.FTLTemplateUtil;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.common.util.TransportConstants;
import com.cintap.transport.entity.edifact.orders.OrdersAddress;
import com.cintap.transport.entity.edifact.orders.OrdersFreeText;
import com.cintap.transport.entity.edifact.orders.OrdersFreeTextDetails;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;
import com.cintap.transport.entity.edifact.orders.OrdersLineItem;
import com.cintap.transport.entity.edifact.orders.OrdersMessageDateTime;
import com.cintap.transport.entity.edifact.orders.OrdersReference;
import com.cintap.transport.repository.common.PartnerPlantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrdersOutboundXmlParser {

	@Autowired
	private FTLTemplateUtil ftlTemplateUtil;

	@Autowired
	PartnerPlantRepository partnerPlantRepository;

	public String convertOrdersToXml(OrdersHeader ordersHeader) throws Exception {
		try {
			String requestStr = null;
			Template template = ftlTemplateUtil.getFTLTemplateByName("orders_response.ftl");
			log.info("Orders Response Mapper - convertinf Order to XML");
			Optional<String> optPartnerPlants = partnerPlantRepository.findCodeByPartnerIdAndPartnerName(
					ordersHeader.getReceiverPartnerId(), ordersHeader.getReceiverAddress());
			String plantCode = "";
			if (optPartnerPlants.isPresent()) {
				plantCode = optPartnerPlants.get();
				Map<String, Object> templateData = new HashMap<>();
				String asusSo = getReferenceIdentifier(ordersHeader.getLstOrdersReference(), "OP");
				String messageNumber = ordersHeader.getLstOrdersHeaderMessages().get(0).getMessageNumber();

				templateData.put("source", plantCode);
				templateData.put("documentCreation", TransportCommonUtility.getDocumentCreation());
				templateData.put("messageNumber", TransportCommonUtility.formatXmlCharacters(messageNumber));
				templateData.put("asusSo", TransportCommonUtility.formatXmlCharacters(asusSo));
				templateData.put("dueDate", getdueDate(ordersHeader.getLstOrdersMessageDateTime()));
				templateData.put("combinedId", getReferenceIdentifier(ordersHeader.getLstOrdersReference(), "ACD"));
				templateData.put("addressIdentifier", getAddressIdentifier(ordersHeader.getLstOrdersAddresses(), "ST"));
				templateData.put("formaPalletType", getFormaPalletType(ordersHeader.getLstOrdersFreeTexts()));
				templateData.put("shippingAddress", getAddress1(ordersHeader.getLstOrdersAddresses(), "ST"));

				List<OrdersLineItem> lstLineItem = ordersHeader.getLstOrdersLineItem();

				templateData.put("lineItems", lstLineItem);

				try (StringWriter out = new StringWriter()) {

					template.process(templateData, out);
					requestStr = out.getBuffer().toString();

					out.flush();
				}
			}
			return requestStr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	private String getReferenceIdentifier(List<OrdersReference> lstReference, String qualifier) {
		for (OrdersReference ordersReference : lstReference) {
			if (ordersReference.getQualifier().equals(qualifier))
				return TransportCommonUtility.formatXmlCharacters(ordersReference.getIdentifier());
		}
		return "";
	}

	private String getdueDate(List<OrdersMessageDateTime> lstMessageDateTime) {
		for (OrdersMessageDateTime ordersMessageDateTime : lstMessageDateTime) {
			if (ordersMessageDateTime.getQualifier().equals("10")) {

				return TransportCommonUtility.convertToDateFormat(ordersMessageDateTime.getDate(),
						TransportConstants.DUE_DATE_FORMAT, TransportConstants.REQUESTED_SHIP_DATE_FORMAT);
			}
		}
		return "";
	}

	private String getAddressIdentifier(List<OrdersAddress> lstAddress, String type) {
		for (OrdersAddress ordersAddress : lstAddress) {
			if (ordersAddress.getType().equals(type))
				return TransportCommonUtility.formatXmlCharacters(ordersAddress.getIdentifier());
		}
		return "";
	}

	private String getAddress1(List<OrdersAddress> lstAddress, String type) {
		for (OrdersAddress ordersAddress : lstAddress) {
			if (ordersAddress.getType().equals(type))
				return TransportCommonUtility.formatXmlCharacters(ordersAddress.getAddress1());
		}
		return "";
	}

	private String getFormaPalletType(List<OrdersFreeText> lstFreeText) {
		for (OrdersFreeText ordersFreeText : lstFreeText) {
			if (ordersFreeText.getQualifier().equals("ALL")) {
				for (OrdersFreeTextDetails ordersFreeTextDetails : ordersFreeText.getLstOrdersFreeTextDetails()) {
					if (ordersFreeTextDetails.getSequence().equals("FORMA"))
						return TransportCommonUtility.formatXmlCharacters(ordersFreeTextDetails.getContent());
				}
			}
		}
		return "";
	}

}
