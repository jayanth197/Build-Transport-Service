package com.cintap.transport.aperak.edifact.parser;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.cintap.transport.aperak.model.AperakData;
import com.cintap.transport.common.ftl.util.FTLTemplateUtil;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.common.PartnerPlantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AperakOutboundXmlParser {

	@Autowired
	private FTLTemplateUtil ftlTemplateUtil;
	
	@Autowired
	PartnerPlantRepository partnerPlantRepository;
	
	public String generateOutbound(AperakData aperakData,TransactionLog transactionLog, FileUploadParams fileUploadParams) {
		log.info("AperakOutboundXmlParser : generateOutbound : " + aperakData);
						
		String xmlData = new String();
		try {
			Template template = ftlTemplateUtil.getFTLTemplateByName("aperak_response.ftl");
			
			Optional<String> optPartnerPlants = partnerPlantRepository.findCodeByPartnerIdAndPartnerName(fileUploadParams.getReceiverPartnerId(), aperakData.getReceiverAddressId());
			String plantCode = "";
			StringBuilder edifactData = new StringBuilder();
			if(optPartnerPlants.isPresent()) {
				plantCode = optPartnerPlants.get();

				Map<String, Object> templateData = new HashMap<>();
				
				templateData.put("documentCreation", TransportCommonUtility.getDocumentCreation());
				templateData.put("messageReference",aperakData.getMessageReferenceNumber());
				templateData.put("source", plantCode);
				templateData.put("documentAck", aperakData.getDocumentName());
				templateData.put("identification", aperakData.getIdentification());
				templateData.put("referenceNumberAck", aperakData.getReferenceNumberAck());
				templateData.put("responseCode", TransportCommonUtility.getResponseCodeValue(aperakData.getResponseCode()));
				
				try (StringWriter out = new StringWriter()) {
	
					template.process(templateData, out);
					xmlData = out.getBuffer().toString();
	
					out.flush();
				}
				
				log.info(transactionLog.getBpiLogId() + " :: Completed Started EDI Convertion");
			}
		} catch (Exception exception) {
			log.info(transactionLog.getBpiLogId() + " :: Got Exception In EDI Convertion :: "+exception.getMessage());
		}

		log.info("AperakOutboundXmlParser : generated output : " + xmlData);
		return xmlData;
	}
	
}
