package com.cintap.transport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingAddress;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingAddressInterlineInfo;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeader;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeaderMessage;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeaderReference;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingLadingException;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingLine;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingLineMessage;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingLineReference;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingSummary;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingWeightPackingQuantityInfo;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.CarrierShipmentTrackingHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;
import com.cintap.transport.service.CarrierShipmentTrackingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarrierShipmentTrackingServiceImpl implements CarrierShipmentTrackingService {

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	CarrierShipmentTrackingHeaderRepository carrierShipmentTrackingHeaderRepository;

	@Override
	public Integer saveCarrierShipment(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader, String txnSource,
			String processType) {

		log.info("CarrierShipmentTrackingServiceImpl | saveCarrierShipment - Request : CarrierShipmentTrackingHeader - "
				+ carrierShipmentTrackingHeader + "TxnSource - " + txnSource);
		Integer transactionLogId = null;

		TransactionLog trnLog = transactionLogRepository
				.save(buildTransactionLog(carrierShipmentTrackingHeader, txnSource, processType));

		transactionLogId = trnLog.getBpiLogId();

		carrierShipmentTrackingHeader.setBpiTransId(transactionLogId);

		carrierShipmentTrackingHeader.setCreatedDate(TransportCommonUtility.getCurrentDateTime());
		carrierShipmentTrackingHeader.setStatus(5);

		if (!CollectionUtils.isEmpty(carrierShipmentTrackingHeader.getCarrierShipmentTrackingAddresses())) {
			for (CarrierShipmentTrackingAddress trackingAddress : carrierShipmentTrackingHeader
					.getCarrierShipmentTrackingAddresses()) {
				carrierShipmentTrackingHeader.addCarrierShipmentTrackingAddresse(trackingAddress);
//				if(!CollectionUtils.isEmpty(trackingAddress.getCarrierShipmentTrackingAddressInterlineInfos())) {
//					for(CarrierShipmentTrackingAddressInterlineInfo addressLineInfo : trackingAddress.getCarrierShipmentTrackingAddressInterlineInfos()) {
//						trackingAddress.addCarrierShipmentTrackingAddressInterlineInfo(addressLineInfo);
//					}
//				}
			}
		}

		if (!CollectionUtils.isEmpty(carrierShipmentTrackingHeader.getCarrierShipmentTrackingHeaderMessages())) {
			for (CarrierShipmentTrackingHeaderMessage headerMessage : carrierShipmentTrackingHeader
					.getCarrierShipmentTrackingHeaderMessages()) {
				carrierShipmentTrackingHeader.addCarrierShipmentTrackingHeaderMessage(headerMessage);
			}
		}

		if (!CollectionUtils.isEmpty(carrierShipmentTrackingHeader.getCarrierShipmentTrackingHeaderReferences())) {
			for (CarrierShipmentTrackingHeaderReference headerReference : carrierShipmentTrackingHeader
					.getCarrierShipmentTrackingHeaderReferences()) {
				carrierShipmentTrackingHeader.addCarrierShipmentTrackingHeaderReference(headerReference);
			}
		}

//		if (!CollectionUtils.isEmpty(carrierShipmentTrackingHeader.getla    getCarrierShipmentTrackingLadingExceptions())) {
//			for (CarrierShipmentTrackingLadingException trackingException : carrierShipmentTrackingHeader
//					.getCarrierShipmentTrackingLadingExceptions()) {
//				carrierShipmentTrackingHeader.addCarrierShipmentTrackingLadingException(trackingException);
//			}
//		}

		if (!CollectionUtils.isEmpty(carrierShipmentTrackingHeader.getCarrierShipmentTrackingLines())) {
			for (CarrierShipmentTrackingLine trackingLine : carrierShipmentTrackingHeader
					.getCarrierShipmentTrackingLines()) {
				carrierShipmentTrackingHeader.addCarrierShipmentTrackingLine(trackingLine);
				if(!CollectionUtils.isEmpty(trackingLine.getCarrierShipmentTrackingLineMessages())) {
					for(CarrierShipmentTrackingLineMessage trackingLineMessage : trackingLine.getCarrierShipmentTrackingLineMessages()) {
						trackingLine.addCarrierShipmentTrackingLineMessage(trackingLineMessage);
					}
				}
				
				if(!CollectionUtils.isEmpty(trackingLine.getCarrierShipmentTrackingLineReferences())) {
					for(CarrierShipmentTrackingLineReference trackingLineReference : trackingLine.getCarrierShipmentTrackingLineReferences()) {
						trackingLine.addCarrierShipmentTrackingLineReference(trackingLineReference);
					}
				}
				
				if(!CollectionUtils.isEmpty(trackingLine.getCarrierShipmentTrackingWeightPackingQuantityInfos())) {
					for(CarrierShipmentTrackingWeightPackingQuantityInfo quantityInfo : trackingLine.getCarrierShipmentTrackingWeightPackingQuantityInfos()) {
						trackingLine.addCarrierShipmentTrackingWeightPackingQuantityInfo(quantityInfo);
					}
				}
			}
		}

		if (!CollectionUtils.isEmpty(carrierShipmentTrackingHeader.getCarrierShipmentTrackingSummaries())) {
			for (CarrierShipmentTrackingSummary trackingSummary : carrierShipmentTrackingHeader
					.getCarrierShipmentTrackingSummaries()) {
				carrierShipmentTrackingHeader.addCarrierShipmentTrackingSummary(trackingSummary);
			}
		}
		carrierShipmentTrackingHeaderRepository.save(carrierShipmentTrackingHeader);

		
		
		log.info("carrierShipmentTrackingHeaderRepository :  saveCarrierShipment successfully {}", transactionLogId);
		return transactionLogId;

	}

	private TransactionLog buildTransactionLog(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader,
			String txnSource, String processType) {
		return TransactionLog.builder()
				// .bpiLogId(bpiLogId)
				// .bpiTransactionId(String.valueOf(bpiTxnId)
				.isaControlId(carrierShipmentTrackingHeader.getIsaControlId())
				// .gsControlId(carrierShipmentTrackingHeader.get)
				.stpId(carrierShipmentTrackingHeader.getSenderPartnerId())
				.rtpId(carrierShipmentTrackingHeader.getReceiverPartnerId())
				// .stpTransId(ediSegment.getBegSegment().getBeg02PoTypeCode())
				// .standard(STANDARD.X12.getStandard())
				.source(txnSource).ediVersion("4010")
				// .stpSourceId(String.valueOf(EDITRANSACTIONTYPE.EDI_810.getValue()))
				.createdDate(TransportCommonUtility.getCurrentDateTime())
				.createdBy(carrierShipmentTrackingHeader.getSenderPartnerId())
				.statusId(CINTAPBPISTATUS.NEW.getStatusId()).senderIsa(carrierShipmentTrackingHeader.getSenderIsaId())
				.receiverIsa(carrierShipmentTrackingHeader.getReceiverIsaId())
				.partnerProcessDate(TransportCommonUtility.getCurrentDateTime())
				// .stpTransId(carrierShipmentTrackingHeader.get)
				// .refCode(invoiceHeader.getStpRefCode())
				// .senderDisplayName(invoiceHeader.getSenderPartnerId()+"-"+ptnrOrgDtl1.getCompanyName())
				// .receiverDisplayName(invoiceHeader.getReceiverPartnerId()+"-"+ptnrOrgDtl2.getCompanyName())
				// .updatedDate(updatedDate)
				// .updatedBy(updatedBy)
				.build();

	}
}
