package com.cintap.transport.edi.qm.parser;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.ediparser.dto.global.HeaderAddress;
import com.cintap.transport.ediparser.dto.segment.x12.AT7;
import com.cintap.transport.ediparser.dto.segment.x12.AT8;
import com.cintap.transport.ediparser.dto.segment.x12.K1;
import com.cintap.transport.ediparser.dto.segment.x12.L11;
import com.cintap.transport.ediparser.dto.segment.x12.LX;
import com.cintap.transport.ediparser.dto.segment.x12.MS1;
import com.cintap.transport.ediparser.dto.segment.x12.MS2;
import com.cintap.transport.ediparser.dto.segment.x12.MS3;
import com.cintap.transport.ediparser.dto.segment.x12.Q7;
import com.cintap.transport.ediparser.dto.transaction.x12.TransportationCarrierShipmentStatusMessage214;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingAddress;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingAddressInterlineInfo;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeader;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeaderMessage;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeaderReference;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingLadingException;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingLine;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingLineReference;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingSummary;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingWeightPackingQuantityInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ShipmentTracking214V4010InboundBuilder {

	public CarrierShipmentTrackingHeader buildShipmentTrackingX12Model(TransportationCarrierShipmentStatusMessage214 transportationCarrierShipmentStatusMessage214,FileUploadParams fileUploadParams){

		log.info("ShipmentTracking214V4010InboundBuilder --> buildShipmentTrackingX12Model ");

		if(null!=transportationCarrierShipmentStatusMessage214) {

			CarrierShipmentTrackingHeader carrierShipmentTrackingHeader = CarrierShipmentTrackingHeader.builder().build();
			//Header
			carrierShipmentTrackingHeader.setCreatedDate(TransportCommonUtility.getCurrentDateTime());
			carrierShipmentTrackingHeader.setCreatedBy(fileUploadParams.getCreatedBy());
			carrierShipmentTrackingHeader.setStatus(1);
			carrierShipmentTrackingHeader.setStControlNumber(transportationCarrierShipmentStatusMessage214.getStSegment().getSt02ControlNumber());
			carrierShipmentTrackingHeader.setIsaControlId(transportationCarrierShipmentStatusMessage214.getIsaSegment().getIsa13InterchangeControlNumber());
			carrierShipmentTrackingHeader.setShipmentNumber(transportationCarrierShipmentStatusMessage214.getB10().getB1002ShipmentIdentificationNumber());
			//carrierShipmentTrackingHeader.setShipmentDate(null);

			//Address
			//List<CarrierShipmentTrackingAddress> lstCarrierShipmentTrackingAddress

			for(HeaderAddress addressObj : transportationCarrierShipmentStatusMessage214.getLstHeaderAddress()) {
				CarrierShipmentTrackingAddress carrierShipmentTrackingAddress = CarrierShipmentTrackingAddress.builder()
						.addressTypeCode(addressObj.getN1Segment().getN101IdentifierCode())
						.addressName(addressObj.getN1Segment().getN102Name())
						.addressLine1(addressObj.getLstN3Segment().get(0).getN301AddressInformation())
						.addressLine2(addressObj.getLstN3Segment().get(0).getN302AddressInformation())
						.locationCodeQual(null!=addressObj.getN1Segment().getN103CodeQualifier()?addressObj.getN1Segment().getN103CodeQualifier():"")
						.addressLocationNo(null!=addressObj.getN1Segment().getN104IdentificationCode()?addressObj.getN1Segment().getN104IdentificationCode():"")
						//.addressAlternateName(null!=transportationCarrierShipmentStatusMessage214.getadd .getAddressAlternateName()?carrierShipmentTrackingAddress.getAddressAlternateName():"")
						.city(null!=addressObj.getN4Segment().getN401CityName()?addressObj.getN4Segment().getN401CityName():"")
						.state(null!=addressObj.getN4Segment().getN402State()?addressObj.getN4Segment().getN402State():"")
						.zipCode(null!=addressObj.getN4Segment().getN403PostalCode()?addressObj.getN4Segment().getN403PostalCode():"")
						.country(null!=addressObj.getN4Segment().getN404CountryCode()?addressObj.getN4Segment().getN404CountryCode():"")
						.build();
				
				carrierShipmentTrackingHeader.addCarrierShipmentTrackingAddresse(carrierShipmentTrackingAddress);

			}

			
			List<MS3> lstMs3 =  transportationCarrierShipmentStatusMessage214.getLstMS3Segment();

			if(!CollectionUtils.isEmpty(lstMs3)) {
				for(MS3 ms3: lstMs3) {
					CarrierShipmentTrackingAddressInterlineInfo carrierShipmentTrackingAddressInterlineInfo = CarrierShipmentTrackingAddressInterlineInfo.builder()
							.scacCode(ms3.getMs301StandardCarrierAlphaCode())
							.routingSequenceCode(ms3.getMs302RoutingSequenceCode())
							.city(ms3.getMs303CityName())
							.transportMethodCode(ms3.getMs304TransportationMethodOrTypeCode())
							.stateProvinceCode(ms3.getMs305StateOrProvinceCode())
							.build();
					carrierShipmentTrackingHeader.addCarrierShipmentTrackingAddressInterlineInfo(carrierShipmentTrackingAddressInterlineInfo);
				}
			}

			//carrier_shipment_tracking_header_message--> K1 segment
			List<K1> lstHeaderMessage = transportationCarrierShipmentStatusMessage214.getLstK1Segment();
			if(!CollectionUtils.isEmpty(lstHeaderMessage)) {
				for(K1 headerMessage : lstHeaderMessage) {
					CarrierShipmentTrackingHeaderMessage carrierShipmentTrackingHeaderMessage = CarrierShipmentTrackingHeaderMessage.builder()
							.freeFormMessage(headerMessage.getK101FreeFormMessage())
							.freeFormMessage2(headerMessage.getK102FreeFormMessage())
							.build();
					carrierShipmentTrackingHeader.addCarrierShipmentTrackingHeaderMessage(carrierShipmentTrackingHeaderMessage);
				}
			}


			// Table: carrier_shipment_tracking_header_reference
			List<L11> lstHeaderReference = transportationCarrierShipmentStatusMessage214.getLstL11Segment();

			if(!CollectionUtils.isEmpty(lstHeaderReference)) {
				for(L11 headerReference : lstHeaderReference) {
					CarrierShipmentTrackingHeaderReference carrierShipmentTrackingHeaderReference = CarrierShipmentTrackingHeaderReference.builder()
							.referenceNumber(headerReference.getL1101ReferenceIdentification())
							.refQualifier(headerReference.getL1102ReferenceIdentificationQualifier())
							.build();
					carrierShipmentTrackingHeader.addCarrierShipmentTrackingHeaderReference(carrierShipmentTrackingHeaderReference);
				}
			}

			// Line Item
			List<LX> lstLX = transportationCarrierShipmentStatusMessage214.getLstLXSegment();
			//summary variables
			Double grossWeight=0.0;
			Integer grossVolume=0;
			Integer ladingQty=0;

			for(LX lx : lstLX) {

				AT7 shipmentStatusDetails = lx.getAt7Segment();
				MS1 propertyLocation = lx.getMs1Segment();
				MS2 containerOwnerAndType = lx.getMs2Segment();
				List<L11> lstReferenceNumber =  lx.getLstL11Segment();
				List<Q7> lstLadingExceptionCode =  lx.getLstQ7Segment();
				List<AT8> lstWeightPackagingQtyData =  lx.getLstAT8Segment();

				CarrierShipmentTrackingLine carrierShipmentTrackingLine  = CarrierShipmentTrackingLine.builder()
						.shipStatusSeqId(lx.getLx01AssignedNumber())
						.shipStatusCode(shipmentStatusDetails.getAt701ShipmentStatusCode())
						.shipStatusReasonCode(shipmentStatusDetails.getAt702ShipmentStatus())
						.shipAppointmentStatusCode(shipmentStatusDetails.getAt703ShipmentAppointmentStatusCode())
						.shipAppointmentReasonCode(shipmentStatusDetails.getAt704ShipmentStatus())
						//.numberOfUnitShipped(shipmentStatusDetails) Need to map
						.date(String.valueOf(shipmentStatusDetails.getAt705Date()))
						.time(String.valueOf(shipmentStatusDetails.getAt706Time()))
						.shipLocationCity(propertyLocation.getMs101CityName())		
						.shipLocationState(propertyLocation.getMs102State())
						.shipLocationCountry(propertyLocation.getMs103Country())
						.carrierScac(containerOwnerAndType.getMs201StandardCarrierAlphaCode())
						.carrierEquipmentId(containerOwnerAndType.getMs202EquipmentNumber())
						.build();

				// carrier_shipment_tracking_line_reference
				if(!CollectionUtils.isEmpty(lstReferenceNumber)) {
					for(L11 referenceDetails : lstReferenceNumber) {
						CarrierShipmentTrackingLineReference carrierShipmentTrackingLineReference = CarrierShipmentTrackingLineReference.builder()
								.refQualifier(referenceDetails.getL1102ReferenceIdentificationQualifier())
								.referenceNumber(referenceDetails.getL1101ReferenceIdentification())
								.build();
						carrierShipmentTrackingLine.addCarrierShipmentTrackingLineReference(carrierShipmentTrackingLineReference);
					}
				}

				//carrier_shipment_tracking_weight_packing_quantity_info
				if(!CollectionUtils.isEmpty(lstWeightPackagingQtyData)) {
					for(AT8 weightPackingQtyDetails : lstWeightPackagingQtyData) {
						CarrierShipmentTrackingWeightPackingQuantityInfo carrierShipmentTrackingWeightPackingQuantityInfo = CarrierShipmentTrackingWeightPackingQuantityInfo.builder()
								.weightQualifier(weightPackingQtyDetails.getAt801WeightQualifier())
								.weightUnitCode(weightPackingQtyDetails.getAt802WeightUnitCode())
								.weight(weightPackingQtyDetails.getAt803Weight())
								.ladingQty(weightPackingQtyDetails.getAt804LadingQty())
								.volume(weightPackingQtyDetails.getAt07Volume())
								.build();
						carrierShipmentTrackingLine.addCarrierShipmentTrackingWeightPackingQuantityInfo(carrierShipmentTrackingWeightPackingQuantityInfo);

						if(null!=weightPackingQtyDetails.getAt803Weight()) {
							grossWeight = grossWeight + Double.parseDouble(weightPackingQtyDetails.getAt803Weight());
						}
						
						if(null!=weightPackingQtyDetails.getAt07Volume()) {
							grossVolume = grossVolume + Integer.parseInt(weightPackingQtyDetails.getAt07Volume());
						}
						
						if(null!=weightPackingQtyDetails.getAt804LadingQty()) {
							ladingQty = ladingQty + Integer.parseInt(weightPackingQtyDetails.getAt804LadingQty());
						}
						
					}
				}
				
				//carrier_shipment_tracking_lading_exception
				if(!CollectionUtils.isEmpty(lstLadingExceptionCode)) {
					for(Q7 ladingExceptionCode : lstLadingExceptionCode) {

						CarrierShipmentTrackingLadingException carrierShipmentTrackingLadingException = CarrierShipmentTrackingLadingException.builder()
								.ladingExceptionCode(ladingExceptionCode.getQ701LadingExceptionCode())
								.packagingFormCode(ladingExceptionCode.getQ702PackagingFormCode())
								.ladingQuantity(null!=ladingExceptionCode.getQ703LadingQuantity()?Integer.parseInt(ladingExceptionCode.getQ703LadingQuantity()):0)
								.build();
						carrierShipmentTrackingLine.addCarrierShipmentTrackingLadingException(carrierShipmentTrackingLadingException);
					}
				}
				carrierShipmentTrackingHeader.addCarrierShipmentTrackingLine(carrierShipmentTrackingLine);
			}//End of Line

			//Summary
			CarrierShipmentTrackingSummary carrierShipmentTrackingSummary = CarrierShipmentTrackingSummary.builder()
					.grossWeight(grossWeight.intValue())
					.volume(grossVolume)
					.ladingQuantity(ladingQty)
					.build();
			carrierShipmentTrackingHeader.addCarrierShipmentTrackingSummary(carrierShipmentTrackingSummary);

			log.info("ShipmentTracking214V4010InboundBuilder - buildShipmentTrackingX12Model : "+TransportCommonUtility.convertObjectToJson(carrierShipmentTrackingSummary));
			return carrierShipmentTrackingHeader;
		}else {
			return null;
		}

	}

}
