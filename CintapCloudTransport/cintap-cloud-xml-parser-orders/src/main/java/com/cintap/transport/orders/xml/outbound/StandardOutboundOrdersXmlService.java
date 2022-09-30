package com.cintap.transport.orders.xml.outbound;

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
import com.cintap.transport.entity.common.TransportServiceMapping;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;
import com.cintap.transport.orders.xml.parser.OrdersOutboundXmlParser;
import com.cintap.transport.repository.edifact.orders.OrdersHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("StandardOutboundOrdersXmlService")
@Slf4j
public class StandardOutboundOrdersXmlService implements StandardOutboundService {

	@Autowired
	OrdersHeaderRepository ordersHeaderRepository;

	@Autowired
	OrdersOutboundXmlParser ordersOutboundXmlParser;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	AuditLogService auditLogService;

	@Override
	public String processOutboundRequest(Integer bpiLogId, FileUploadParams fileUploadParams,
			TransportServiceMapping transportServiceMapping) {
		String xmlData = null;
		try {
			log.info("OrdersService - sendOrders : Before template creation");
			Optional<OrdersHeader> optOrdersHeader = ordersHeaderRepository.findByBpiLogId(bpiLogId);

			if (optOrdersHeader.isPresent()) {
				OrdersHeader ordersHeader = optOrdersHeader.get();

				xmlData = ordersOutboundXmlParser.convertOrdersToXml(ordersHeader);

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
						TRANSPORTLOG.INITIATED_XML_CONVERTION.getValue(), 0);

				auditLogService.saveAuditLog(ordersHeader.getSenderPartnerId(), bpiLogId,
						COMPONENTNAME.TRANSACTION.getComponentName(), bpiLogId, ACTIONTYPE.CREATE.getAction(),
						"ORDER OUTBOUND XML generated successfully # " + bpiLogId);

			} else {
				log.info("StandardInboundDesAdvAsnEdiFactService : Unable to convert into Entity");

				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),
						TRANSPORTLOG.NO_OF_TRANSACTIONS_IDENTIFIED.getValue(), 0);
				return xmlData;
			}

			log.info("OrdersService - sendOrders :ORDERS Request : " + xmlData);

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
