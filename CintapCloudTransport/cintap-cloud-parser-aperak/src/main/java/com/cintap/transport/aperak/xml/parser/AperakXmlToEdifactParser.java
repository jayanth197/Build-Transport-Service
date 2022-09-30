package com.cintap.transport.aperak.xml.parser;

import java.util.List;
import java.util.Optional;

import com.cintap.transport.aperak.model.AperakData;
import com.cintap.transport.aperak.model.Interface;
import com.cintap.transport.aperak.model.Reference;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AperakXmlToEdifactParser {

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	AperakXmlParser AperakXmlParser;
	
	@Autowired
	AperakOutboundEdifactParser aperakOutboundEdifactParser;

	public FileUploadParams convertModelToEntity(String fileData, FileUploadParams fileUploadParams) {
		log.info("AperakInboundEdifactParser : convertModelToEntity : request xml : " + fileData);

		Interface inter = AperakXmlParser.convertXmlTo(fileData);

		log.info("AperakInboundEdifactParser : convertModelToEntity : JSON Object : ");
		AperakData aperakData = null;

		String identification = inter.getAcknowledgment().getIdentification();

		Optional<TransactionLog> optTransactionLog = transactionLogRepository.findByStpIdAndRtpIdAndStpTransId(
				 fileUploadParams.getReceiverPartnerId(), fileUploadParams.getSenderPartnerId(), identification);

		if (optTransactionLog.isPresent()) {
			TransactionLog transactionLog = optTransactionLog.get();
			Integer bpiLogId = transactionLog.getBpiLogId();
			log.info("AperakInboundEdifactParser : convertModelToEntity : Bpi Log Id : " + bpiLogId);

			fileUploadParams.setBpiLogId(bpiLogId);

			aperakData = AperakData.builder().bpiLogId(bpiLogId)
					.ackType(inter.getAcknowledgment().getDocumentAck())
					.messageReferenceNumber(inter.getHeader().getMessageReference())
					.refUnh01(getRefData(inter.getHeader().getReferenceList(), "@UHN01"))
					.refSource(getRefData(inter.getHeader().getReferenceList(), "Source"))
					.identification(identification)
					.referenceNumberAck(inter.getAcknowledgment().getReferenceNumberAck())
					.responseCode(inter.getAcknowledgment().getResponseCode())
					.responseReason(inter.getAcknowledgment().getReason())
					.referenceNumber(getRefData(inter.getAcknowledgment().getDetails().getReferenceList(), "REF+ACD:02"))
					.bgmReferenceNumber(getRefData(inter.getAcknowledgment().getDetails().getReferenceList(), "BGM+220:04"))
					.documentCreationDate(inter.getHeader().getDocumentCreation()).build();

			fileUploadParams.setAckRefNum(aperakData.getReferenceNumberAck());
			fileUploadParams.setAckType(aperakData.getResponseCode());
			
			log.info("AperakInboundEdifactParser : convertModelToEntity : Edifact Ack Logs saved : "
					+ aperakData);
			
			String edifactData = aperakOutboundEdifactParser.generateOutbound(aperakData, transactionLog, fileUploadParams);
			fileUploadParams.setOutboundRawFile(edifactData);
		} else {
			log.info("AperakInboundEdifactParser : convertModelToEntity : no record found");
		}

		return fileUploadParams;
	}

	private String getRefData(List<Reference> lstReference, String key) {
		String value = "";
//		log.info("AperakInboundEdifactParser : getRefHeader : Request -- Header: " + header + "key:" + key);
		for (Reference ref : lstReference) {
			if (key.equals(ref.getKey())) {
				value = String.valueOf(ref.getTextContent());
				break;
			}
		}
		log.info("AperakInboundEdifactParser : getRefHeader : response" + value);
		return value;
	}
}
