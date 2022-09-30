package com.cintap.transport.desadv.xml.parser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cintap.transport.common.ftl.util.FTLTemplateUtil;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceAddress;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvLineItem;
import com.cintap.transport.entity.edifact.desadv.EdifactDesadvReference;
import com.cintap.transport.repository.common.PartnerPlantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DespatchAdviceOuboundXmlParser {
	
	@Autowired
	private FTLTemplateUtil ftlTemplateUtil;
	
	@Autowired
	PartnerPlantRepository partnerPlantRepository;

	public String convertDesadvAsnToXml(DespatchAdviceHeader edifactDesadvHeader) throws Exception {
		try {
			String requestStr = null;
			Template template = ftlTemplateUtil.getFTLTemplateByName("desadv_asn_response.ftl");
			log.info("DesadvASNRequestMapper - mapToDesadvASNRequest");
			Optional<String> optPartnerPlants = partnerPlantRepository.findCodeByPartnerIdAndPartnerName(edifactDesadvHeader.getReceiverPartnerId(), edifactDesadvHeader.getReceiverAddress());
			String plantCode = "";
			if (optPartnerPlants.isPresent()) {
				plantCode = optPartnerPlants.get();
				Map<String, Object> templateData = new HashMap<>();

				templateData.put("source", plantCode);
				templateData.put("documentCreation", TransportCommonUtility.getDocumentCreation());
				templateData.put("messageNumber",
						edifactDesadvHeader.getEdifactDesadvHeaderMessages().get(0).getMessageNumber());
				templateData.put("bgmDocMsgNumber", edifactDesadvHeader.getBgmDocMsgNumber());
				templateData.put("currency", edifactDesadvHeader.getEdifactDesadvSummaries().get(0).getCurrency());
				templateData.put("invoiceAmount",
						edifactDesadvHeader.getEdifactDesadvSummaries().get(0).getInvoiceAmount());
				templateData.put("addreessIdentifier",
						getAddressIdentifier(edifactDesadvHeader.getLstEdifactDesadvAddresses()));

				List<EdifactDesadvLineItem> lstEdifactLineItems = edifactDesadvHeader.getEdifactDesadvLineItems();

				List<EdifactDesadvReference> lstEdifactReference = edifactDesadvHeader.getEdifactDesadvReferences();

				List<EdifactDesadvReference> lstReferences = null;

				if (!CollectionUtils.isEmpty(lstEdifactReference)) {
					lstReferences = lstEdifactReference.stream().filter(p -> p.getQualifier().equalsIgnoreCase("ON"))
							.collect(Collectors.toList());
				

				int i = 0;
				for (EdifactDesadvLineItem edifactDesadvLineItem : lstEdifactLineItems) {
//				templateData.put("itemLineNumber", edifactDesadvLineItem.getItemLineNumber());
//				templateData.put("referenceIdentifier", i<lstReferences.size() ? lstReferences.get(i) : "");
//				templateData.put("vendorPartnerDescription", edifactDesadvLineItem.getVendorPartDescription());
//				templateData.put("vendorPartnerNumber", edifactDesadvLineItem.getVendorPartNumber());
//				templateData.put("quantity", edifactDesadvLineItem.getQuantity());
//				templateData.put("unitPrice", edifactDesadvLineItem.getUnitPrice());
					edifactDesadvLineItem.setReferenceIdentifier(
							i < lstReferences.size() ? lstReferences.get(i).getIdentifier() : "");
					i++;
				}
				}

				templateData.put("lineItems", lstEdifactLineItems);

				
				try (StringWriter out = new StringWriter()) {

					template.process(templateData, out);
					requestStr = out.getBuffer().toString();
					log.info("DesadvASNRequestMapper - mapToDesadvASNRequest : Request : " + requestStr);

					out.flush();
				}
			}
			return requestStr;
		} catch (Exception e) {
			log.info("DesadvASNRequestMapper - mapToDesadvASNRequest : Got Error");
			e.printStackTrace();
			throw e;
		}

	}
	
	private String getReferenceIdentifier(List<EdifactDesadvReference> edifactDesadvReferences) {
		String value = "";
		for(EdifactDesadvReference edifactReference: edifactDesadvReferences)
		{
			if(edifactReference.getQualifier().equals("ON")) {
				value = edifactReference.getIdentifier();
				break;
			}
		}
		return value;
	}
	
	private String getAddressIdentifier(List<DespatchAdviceAddress> edifactDesadvAddresses) {
		String value = "";
		for(DespatchAdviceAddress edifactAddress: edifactDesadvAddresses)
		{
			if(edifactAddress.getType().equals("SF")) {
				value = edifactAddress.getIdentifier();
				break;
			}
		}
		return value;
	}

}
