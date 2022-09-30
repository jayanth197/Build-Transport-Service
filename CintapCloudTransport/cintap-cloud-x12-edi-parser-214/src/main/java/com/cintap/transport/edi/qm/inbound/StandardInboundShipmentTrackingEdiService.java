package com.cintap.transport.edi.qm.inbound;

import java.util.List;
import java.util.Optional;

import com.cintap.transport.common.enums.ACTIONTYPE;
import com.cintap.transport.common.enums.CINTAPBPISTATUS;
import com.cintap.transport.common.enums.COMPONENTNAME;
import com.cintap.transport.common.enums.EDITRANSACTIONTYPE;
import com.cintap.transport.common.enums.LOGTYPE;
import com.cintap.transport.common.enums.STANDARD;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.enums.TRANSPORTLOG;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.service.AuditLogService;
import com.cintap.transport.common.service.TransactionTrailService;
import com.cintap.transport.common.service.TransportCommonService;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.edi.qm.parser.ShipmentTracking214V4010InboundBuilder;
import com.cintap.transport.ediparser.dto.global.Response;
import com.cintap.transport.ediparser.dto.transaction.x12.TransportationCarrierShipmentStatusMessage214;
import com.cintap.transport.entity.common.AuditLog;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeader;
import com.cintap.transport.entity.trans.BpiLogDetail;
import com.cintap.transport.entity.trans.BpiLogHeader;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.entity.trans.TransactionTrail;
import com.cintap.transport.repository.CarrierShipmentTrackingHeaderRepository;
import com.cintap.transport.repository.TransactionTrailRepository;
import com.cintap.transport.repository.trans.AuditLogRepository;
import com.cintap.transport.repository.trans.BatchDetailsRepository;
import com.cintap.transport.repository.trans.BpiLogDetailRepository;
import com.cintap.transport.repository.trans.BpiLogHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StandardInboundShipmentTrackingEdiService {

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	TransactionTrailRepository transactionTrailRepository;

	@Autowired
	TransportCommonService transportCommonService;

	@Autowired
	ShipmentTracking214V4010InboundBuilder shipmentTracking214V4010InboundBuilder;

	@Autowired
	CarrierShipmentTrackingHeaderRepository carrierShipmentTrackingHeaderRepository;

	@Autowired
	BpiLogHeaderRepository bpiLogHeaderRepository;

	@Autowired
	BatchDetailsRepository batchDetailsRepository;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	BpiLogDetailRepository bpiLogDetailRepository;

	@Autowired
	TransactionTrailService transactionTrailService;

	@Autowired
	AuditLogRepository auditLogRepository;

	public FileUploadParams processShipmentTracking(Response response,FileUploadParams fileUploadParams) {

		log.info("StandardInboundShipmentTrackingEdiService | processShipmentTracking - Request");
		TransactionLog transactionLog = null;
		int trnCount = 0;
		List<TransportationCarrierShipmentStatusMessage214> lstTransportationCarrierShipmentStatusMessage214 = (List<TransportationCarrierShipmentStatusMessage214>) response.getData();

		log.info(""+lstTransportationCarrierShipmentStatusMessage214.size());

		trnCount = lstTransportationCarrierShipmentStatusMessage214.size();
		fileUploadParams.setTxnCount(trnCount);
		int initialValue=0;
		for(TransportationCarrierShipmentStatusMessage214 transportationCarrierShipmentStatusMessage214 : lstTransportationCarrierShipmentStatusMessage214) {
			try {
				initialValue++;
				CarrierShipmentTrackingHeader carrierShipmentTrackingHeader = shipmentTracking214V4010InboundBuilder.buildShipmentTrackingX12Model(transportationCarrierShipmentStatusMessage214, fileUploadParams);

				//Save into transaction table
				TransactionLog transactionLogObj = buildTransactionLog(transportationCarrierShipmentStatusMessage214, fileUploadParams);
				transactionLog = transactionLogRepository.save(transactionLogObj);
				if(null!=transactionLog && transactionLog.getBpiLogId()>0) {

					transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
							TRANSPORTLOG.FILE_PARSING_SUCCESSFUL.getValue() + " bpiLogId : " + transactionLog.getBpiLogId(),
							fileUploadParams.getProcessId());

					/**
					 * Dump details into Header and log details if transaction has more than one;  For fist transaction, no need to insert again as it is inserted in MessageProcessHandler
					 */
					if (trnCount > 1 && 1!=initialValue) {
						fileUploadParams = dumpLogDetails(fileUploadParams);
					}

					fileUploadParams.setBpiLogId(transactionLog.getBpiLogId());
					carrierShipmentTrackingHeader.setBpiTransId(transactionLog.getBpiLogId());
					carrierShipmentTrackingHeader.setSenderPartnerId(fileUploadParams.getSenderPartnerId());
					carrierShipmentTrackingHeader.setReceiverPartnerId(fileUploadParams.getReceiverPartnerId());
					carrierShipmentTrackingHeader.setSenderIsaId(fileUploadParams.getSenderIsaId());
					carrierShipmentTrackingHeader.setReceiverIsaId(fileUploadParams.getReceiverIsaId());
					carrierShipmentTrackingHeader.setCreatedBy(fileUploadParams.getPartnerId());
					CarrierShipmentTrackingHeader carrierShipmentTrackingHeaderObj = carrierShipmentTrackingHeaderRepository.save(carrierShipmentTrackingHeader);
					log.info("Shipment Tracking is Saved : " + carrierShipmentTrackingHeaderObj.getId());

					auditLogService.saveAuditLog(carrierShipmentTrackingHeader.getSenderPartnerId(), //partnerid
							transactionLog.getBpiLogId(),
							COMPONENTNAME.TRANSACTION.getComponentName(), //Component Name
							transactionLog.getBpiLogId(), //bpi log id 
							
							ACTIONTYPE.CREATE.getAction(), 
							"Transaction Shipment Tracking created successfully # "+transactionLog.getBpiLogId());  

					bpiLogHeaderRepository.updateBpiLogId(
							transactionLog.getBpiLogId() != null ? transactionLog.getBpiLogId().intValue() : 0,
									fileUploadParams.getBpiHeaderLogId());
					fileUploadParams.setBatchStatus(CINTAPBPISTATUS.SUCCESS.getStatusValue());

					batchDetailsRepository.updateStatus(
							CINTAPBPISTATUS.NEW.getStatusId() + "-" + CINTAPBPISTATUS.NEW.getStatusValue(),
							TransportCommonUtility.getCurrentDateTime(), 1, "Successfully processed " + 1 + " transaction(s)",
							fileUploadParams.getBatchId());

					transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.INFO.getValue(),
							TRANSPORTLOG.TRANSACTION_CREATED.getValue() + " Id : " + carrierShipmentTrackingHeaderObj.getId(),
							fileUploadParams.getProcessId());

					// Update Status to SUCCESS
					fileUploadParams.setBatchStatus(CINTAPBPISTATUS.SUCCESS.getStatusValue());
					transportCommonService.updateBpiHeader(fileUploadParams);
					TransactionTrail transactionTrail = TransactionTrail.builder()
							.bpiLogId(transactionLog.getBpiLogId())
							.masterBpiLogId(transactionLog.getBpiLogId())
							.senderPartner(fileUploadParams.getSenderPartnerId())
							.receiverPartner(fileUploadParams.getReceiverPartnerId())
							.masterTransId(transportationCarrierShipmentStatusMessage214.getB10().getB1002ShipmentIdentificationNumber())
							.masterTransType("QM")
							.createdBy(fileUploadParams.getPartnerId())
							.createdDate(TransportCommonUtility.getCurrentDateTime())
							.isMaster(1).build();
					transactionTrailRepository.save(transactionTrail); 

				}
			}catch(Exception e) {
				log.info("Exception is : "+e);
				String msg=e.getMessage();
				log.info(fileUploadParams.getBatchId() + " :: Failed processing EDI file :: "+msg);
				fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());

				if (e instanceof DataIntegrityViolationException) {
					msg = "Duplicate Transaction";
				}
				transportCommonService.saveBpiLogDetail(fileUploadParams.getBpiHeaderLogId(), LOGTYPE.ERROR.getValue(),msg, 0);
				udpateTransactionLogStatus(transactionLog.getBpiLogId(),msg,2);
				// Update Status to SUCCESS
				fileUploadParams.setBatchStatus(CINTAPBPISTATUS.ERROR.getStatusValue());
				transportCommonService.updateBpiHeader(fileUploadParams);

				/**
				 *  If file contains only one transaction, then throw exception otherwise ignore.  Because, it has to process rest of the orders.
				 */

				if(trnCount==1) {
					throw e;
				}

			}
		}//for end

		return fileUploadParams;
	}

	private TransactionLog buildTransactionLog(TransportationCarrierShipmentStatusMessage214 transportationCarrierShipmentStatusMessage214, FileUploadParams fileUploadParams) {
		TransactionLog transactionLog;

		transactionLog = TransactionLog.builder()
				.batchId(fileUploadParams.getBatchId())
				//.isaControlId(transportationCarrierShipmentStatusMessage214.getIsaSegment().getst .getIsaControlId())
				.processType(TRANSACTIONSOURCE.ELECTRONIC.getValue())
				.senderIsa(fileUploadParams.getSenderIsaId())
				.receiverIsa(fileUploadParams.getReceiverIsaId())
				.stpTransId(transportationCarrierShipmentStatusMessage214.getB10().getB1002ShipmentIdentificationNumber())
				.stpSourceId("" + EDITRANSACTIONTYPE.X_12_EDI_214.getValue())
				.stpId(fileUploadParams.getSenderPartnerId())
				.rtpId(fileUploadParams.getReceiverPartnerId())
				.ediVersion(fileUploadParams.getEdiVersion())
				//.stControlNumber(isaSegment.getStControlNumber())
				.transactionType("QM")
				.fileType(fileUploadParams.getActualFileType())
				.batchId(fileUploadParams.getBatchId())
				//.partnerProcessDate(   orderX12Header.getPurchaseOrderDate())
				.createdDate(TransportCommonUtility.getCurrentDateTime())
				.createdBy(fileUploadParams.getPartnerId())
				.statusId(CINTAPBPISTATUS.NEW.getStatusId())
				.source(fileUploadParams.getSource())
				//.ack997Status(1)
				//.gsFunctionalIdentCode(transportationCarrierShipmentStatusMessage214.getGsSegment().getGs01FunctionalIdentifierCode())
				.isaControlId(transportationCarrierShipmentStatusMessage214.getIsaSegment().getIsa13InterchangeControlNumber())
				.gsControlId(transportationCarrierShipmentStatusMessage214.getGsSegment().getGs06GroupControlNumber())
				.standard(STANDARD.X12.getStandard()).build();

		return transactionLog;
	}
	private void udpateTransactionLogStatus(Integer bpiLogId,String errorMSg,Integer status) {
		Optional<TransactionLog> optTrnLog = transactionLogRepository.findByBpiLogId(bpiLogId);
		if(optTrnLog.isPresent()) {
			TransactionLog trnlog = optTrnLog.get();
			trnlog.setStatusId(status);
			trnlog.setUpdatedDate(TransportCommonUtility.getCurrentDateTime());
			transactionLogRepository.save(trnlog);
			AuditLog auditLog = AuditLog.builder()
					.bpiLogId(trnlog.getBpiLogId())
					.createdDate(TransportCommonUtility.getCurrentDateTime())
					.componentName(COMPONENTNAME.TRANSACTION.getComponentName())
					.componentRecId(trnlog.getBatchId()) //Storing batch id
					.actionType(ACTIONTYPE.UPDATE.getAction())
					.action(errorMSg)
					.createdBy(StringUtils.isNoneEmpty(trnlog.getStpId())?trnlog.getStpId():"0")
					.build();
			auditLogRepository.save(auditLog);
			log.info(bpiLogId+" :: UPDATED TRANSACTION LOG AND AUDIT LOG TABLES "+status+" STATUS :: ");
		}
	}

	private FileUploadParams dumpLogDetails(FileUploadParams fileUploadParams) {
		BpiLogHeader bpiHeaderLog = transportCommonService.saveBpiHeader(fileUploadParams);
		log.info("GROUP ID IS : "+fileUploadParams.getGroupId()+", and actual is  : "+bpiHeaderLog.getLogHdrId()+" GROUP ID : "+bpiHeaderLog.getGroupId());
		fileUploadParams.setBpiHeaderLogId(bpiHeaderLog.getLogHdrId());
		if (!CollectionUtils.isEmpty(fileUploadParams.getLstLogDetails())) {
			for (BpiLogDetail bpiLogDetail : fileUploadParams.getLstLogDetails()) {
				bpiLogDetail.setLogHeaderId(bpiHeaderLog.getLogHdrId());
				bpiLogDetail.setLogDetailId(null);
				bpiLogDetail.setCreatedDate(TransportCommonUtility.getCurrentDateTime());
				bpiLogDetailRepository.save(bpiLogDetail);
			}
		}
		return fileUploadParams;
	}
}
