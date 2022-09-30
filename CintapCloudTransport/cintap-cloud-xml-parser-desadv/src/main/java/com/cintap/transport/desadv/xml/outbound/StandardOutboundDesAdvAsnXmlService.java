package com.cintap.transport.desadv.xml.outbound;

import java.util.Optional;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.StandardOutboundService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.RestTemplateUtil;
import com.cintap.transport.desadv.xml.parser.DespatchAdviceOuboundXmlParser;
import com.cintap.transport.entity.common.TransportServiceMapping;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.repository.edifact.desadv.DespatchAdviceHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("StandardOutboundDesAdvAsnXmlService")
@Slf4j
public class StandardOutboundDesAdvAsnXmlService implements StandardOutboundService {

	@Autowired
	DespatchAdviceOuboundXmlParser despatchAdviceOuboundXmlParser;

	@Autowired
	private RestTemplateUtil restTemplateUtil;

	@Autowired
	DespatchAdviceHeaderRepository edifactDesadvHeaderRepository;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	AuditLogService auditLogService;

	@Override
	public String processOutboundRequest(Integer bpiLogId, FileUploadParams fileUploadParams,
			TransportServiceMapping transportServiceMapping) {
		String xmlData = null;
		try {
			log.info("DesadvASNService - sendDesadvASN : Before template creation");
			Optional<DespatchAdviceHeader> optDespatchAdviceHeader = edifactDesadvHeaderRepository
					.findByBpiLogId(bpiLogId);

			if (optDespatchAdviceHeader.isPresent()) {
				DespatchAdviceHeader despatchAdviceHeader = optDespatchAdviceHeader.get();

				xmlData = despatchAdviceOuboundXmlParser.convertDesadvAsnToXml(despatchAdviceHeader);
				if (xmlData == null) {
					log.info("StandardInboundDesAdvAsnEdiFactService : Plant code not found");

					transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
							LOGTYPE.ERROR.getValue(), TRANSPORTLOG.PLANT_CODE_NOT_FOUND.getValue(), 0);
					return xmlData;
				} else {
					transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(),
							LOGTYPE.INFO.getValue(), TRANSPORTLOG.INITIATED_XML_CONVERTION.getValue(), 0);

					auditLogService.saveAuditLog(despatchAdviceHeader.getSenderPartnerId(), bpiLogId,
							COMPONENTNAME.TRANSACTION.getComponentName(), bpiLogId, ACTIONTYPE.CREATE.getAction(),
							"DESADV ASN OUTBOUND XML generated successfully # " + bpiLogId);
				}
			} else {
				log.info("StandardInboundDesAdvAsnEdiFactService : Unable to convert into Entity");

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
						TRANSPORTLOG.TRANSACTION_NOT_FOUND.getValue(), 0);
				return xmlData;
			}
			log.info("DesadvASNService - sendDesadvASN : DESADV ASN Request : " + xmlData);
		} catch (Exception e) {
			log.info("Exception is : " + e);
			String msg = e.getMessage();
			log.info(fileUploadParams.getBatchId() + " :: Failed processing EDI file :: " + msg);
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());

			if (e instanceof DataIntegrityViolationException) {
				msg = "Duplicate Transaction";
				/**
				 * Reject transaction in the case of Duplicate Order
				 */

			}

			fileUploadParams.setTransactionErrorReason(msg);
			transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(), msg,
					0);
			fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());
			transportCommonService.updateBpiHeader(fileUploadParams);
		}
		return xmlData;
	}

}
