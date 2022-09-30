package com.cintap.transport.desadv.shipnotice.xml.parser;

import java.util.List;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.desadv.shipnotice.model.Carton;
import com.cintap.transport.desadv.shipnotice.model.CartonItem;
import com.cintap.transport.desadv.shipnotice.model.Cartons;
import com.cintap.transport.desadv.shipnotice.model.Height;
import com.cintap.transport.desadv.shipnotice.model.Interface;
import com.cintap.transport.desadv.shipnotice.model.Length;
import com.cintap.transport.desadv.shipnotice.model.Piece;
import com.cintap.transport.desadv.shipnotice.model.Reference;
import com.cintap.transport.desadv.shipnotice.model.Shipment;
import com.cintap.transport.desadv.shipnotice.model.Weight;
import com.cintap.transport.desadv.shipnotice.model.Width;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeaderReference;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItem;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCarton;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCartonItem;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCartonItemSerialNumber;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipment;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipmentAddress;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeShipmentReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DespatchAdviceShipNoticeInboundXmlParser {

	@Autowired
	DespatchAdviceShipNoticeXmlParser despatchAdviceShipNoticeXmlParser;

	public DespatchAdviceShipNoticeHeader convertXmlToEntity(String fileData, FileUploadParams fileUploadParams)
			throws Exception {
		try {
			log.info("DespatchAdviceShipNoticeInboundXmlParser - convertXmlToEntity : fileDate" + fileData);
			Interface inter = despatchAdviceShipNoticeXmlParser.convertXmlTo(fileData);

			if(null==inter) {
				log.info("DespatchAdviceShipNoticeInboundXmlParser - convertXmlToEntity : Got exception converting xml to model" );
				return null;
			}
			DespatchAdviceShipNoticeHeader despatchAdviceShipNoticeHeader = DespatchAdviceShipNoticeHeader.builder()
					.tenantId(inter.getHeader().getTenantID())
					.receiverCode(inter.getHeader().getReceiverCode())
					.senderCode(inter.getHeader().getSenderCode())
					.documentCreationDate(inter.getHeader().getDocumentCreation())
					.messageReference(inter.getHeader().getMessageReference())
					.documentName(inter.getDocument())
					.identification(inter.getShipment().getIdentification())
					.build();
			for (Reference reference : inter.getHeader().getReference()) {
				despatchAdviceShipNoticeHeader
						.addDespatchAdviceShipNoticeHeaderReference(buildHeaderReference(reference));
			}
			despatchAdviceShipNoticeHeader.addDespatchAdviceShipNoticeShipment(buildShipment(inter.getShipment()));
			for(Piece piece: inter.getShipment().getPieces().getPiece()) {
				despatchAdviceShipNoticeHeader.addDespatchAdviceShipNoticeLineItem(buildLineItem(piece));
			}
			
			return despatchAdviceShipNoticeHeader;
		} catch (Exception e) {
			log.info("DespatchAdviceShipNoticeInboundXmlParser - convertXmlToEntity : Got Error");
			e.printStackTrace();
			throw e;
		}
	}

	private DespatchAdviceShipNoticeHeaderReference buildHeaderReference(Reference reference) {
		return DespatchAdviceShipNoticeHeaderReference
				.builder()
				.key(reference.getKey())
				.value(reference.getText())
				.build();
	}
	
	private DespatchAdviceShipNoticeShipment buildShipment(Shipment shipment) {
		DespatchAdviceShipNoticeShipment despatchAdviceShipNoticeShipment = DespatchAdviceShipNoticeShipment
				.builder()
				.identification(shipment.getIdentification())
				.status(shipment.getStatus())
				.internalId(shipment.getInternalId())
				.primaryContactName(shipment.getDetails().getPrimaryContact().getName())
				.enteredTime(shipment.getDetails().getEnteredTime())
				.pickedTime(shipment.getDetails().getPickedTime())
				.packagedTime(shipment.getDetails().getPackagedTime())
				.forwadedTime(shipment.getDetails().getForwardedTime())
				.exportedTime(shipment.getDetails().getExportedTime())
				.subTotal(shipment.getDetails().getSubtotal())
				.notes(shipment.getDetails().getNotes())
				.destinationCountry(shipment.getDetails().getCountryOfUltimateDestination())
				.grossWeightKg(buildWeight("gross","kg",shipment.getDetails().getWeight()))
				.grossWeightLb(buildWeight("gross","lb",shipment.getDetails().getWeight()))
				.build();
		for (Reference ref : shipment.getDetails().getReference()) {
			despatchAdviceShipNoticeShipment.addDespatchAdviceShipNoticeShipmentReference(buildShipmentReference(ref));
		}
		despatchAdviceShipNoticeShipment.addDespatchAdviceShipNoticeShipmentAddress(buildShipmentAddress(shipment));
		return despatchAdviceShipNoticeShipment;
	}
	
	private DespatchAdviceShipNoticeShipmentReference buildShipmentReference(Reference ref){
		return DespatchAdviceShipNoticeShipmentReference
				.builder()
				.key(ref.getKey())
				.value(ref.getText())
				.build();
	}

	private DespatchAdviceShipNoticeShipmentAddress buildShipmentAddress(Shipment shipment){
		return DespatchAdviceShipNoticeShipmentAddress.builder()
				.addressType("Warehouse")
				.company(shipment.getWarehouse().getCompany())
				.addressLine1(shipment.getWarehouse().getAddressLine().get(0))
				.addressLine2(shipment.getWarehouse().getAddressLine().get(1))
				.city(shipment.getWarehouse().getCity())
				.state(shipment.getWarehouse().getState())
				.postalCode(shipment.getWarehouse().getPostalCode())
				.country(shipment.getWarehouse().getCountry())
				.locationNumber(shipment.getWarehouse().getInternalId())
				.build();
	}
	
	private DespatchAdviceShipNoticeLineItem buildLineItem(Piece piece) {
		DespatchAdviceShipNoticeLineItem lineItem = DespatchAdviceShipNoticeLineItem.builder()
				.isWood(piece.isWood())
				.isHazardous(piece.isHazardous())
				.isLithium(piece.isLithium())
				.itemType(piece.getPieceType())
				.itemSerial(piece.getPieceSerial())
				.itemNumber(piece.getPieceNumber())
				.cartonCount(piece.getCartonCount())
				.grossWeightKg(buildWeight("gross", "kg", piece.getWeight()))
				.grossWeightLb(buildWeight("gross", "lb", piece.getWeight()))
				.netWeightKg(buildWeight("net", "kg", piece.getWeight()))
				.netWeightLb(buildWeight("net", "lb", piece.getWeight()))
				.lengthCm(buildLength("cm", piece.getLength()))
				.lengthInch(buildLength("inch", piece.getLength()))
				.widthCm(buildWidth("cm", piece.getWidth()))
				.widthInch(buildWidth("inch", piece.getWidth()))
				.heightCm(buildHeight("cm", piece.getHeight()))
				.heightInch(buildHeight("inch", piece.getHeight()))
				.remarks(piece.getRemark()).build();
		lineItem = buildCarton(piece.getCartons(), lineItem);
	
		return lineItem;
	}
	
	private DespatchAdviceShipNoticeLineItem buildCarton(Cartons cartons, DespatchAdviceShipNoticeLineItem lineItem) {
		for (Carton carton : cartons.getCarton()) {
			DespatchAdviceShipNoticeLineItemCarton lineItemCarton = DespatchAdviceShipNoticeLineItemCarton.builder()
					.cartonId(carton.getCartonID())
					.createdDateTime(carton.getCreated())
					.isWood(carton.isWood())
					.isHazardous(carton.isHazardous())
					.isLithium(carton.isLithium())
					.conditionCode(carton.getConditionCode())
					.customerConditionCode(carton.getCustomerConditionCode())
					.qccPerson(carton.getQCC().getPerson())
					.isQcc(carton.getQCC().isText())
					.salesOrder(carton.getSalesOrder())
					.poNumber(carton.getPurchaseOrder())
					.shipSet(carton.getShipSet())
					.vendorCartonId(carton.getVendorCartonID())
					.grossWeightKg(buildWeight("gross", "kg", carton.getWeight()))
					.grossWeightLb(buildWeight("gross", "lb", carton.getWeight()))
					.inboundTrackingCarrier(carton.getInboundTracking().getCarrier())
					.inboundTrackingCarrierCode(carton.getInboundTracking().getText()).build();
			
			lineItemCarton.addDespatchAdviceShipNoticeLineItemCartonItem(buildCartonItem(carton.getCartonItems().getCartonItem()));
			lineItem.addDespatchAdviceShipNoticeLineItemCarton(lineItemCarton);
		}
		return lineItem;
	}

	private DespatchAdviceShipNoticeLineItemCartonItem buildCartonItem(CartonItem cartonItem) {
		DespatchAdviceShipNoticeLineItemCartonItem lineItemCartonItem = DespatchAdviceShipNoticeLineItemCartonItem.builder()
				.lineSequenceNumber(cartonItem.getLIN())
				.itemQuantity(cartonItem.getItemQuantity())
				.partNumber(cartonItem.getPartNumber())
				.scheduleB(cartonItem.getScheduleB())
				.eccn(cartonItem.getECCN())
				.unitPrice(cartonItem.getUnitPrice().getText())
				.unitPriceCurrencyCode(cartonItem.getUnitPrice().getCurrencyCode())
				.extendedPrice(cartonItem.getExtendedPrice().getText())
				.extendedPriceCurrencyCode(cartonItem.getExtendedPrice().getCurrencyCode())
				.partDescription(cartonItem.getPartDescription())
				.customerPartDescription(cartonItem.getCustomPartDescription())
				.countryOfOrigin(cartonItem.getCountryOfOrigin())
				.isExportRestricted(cartonItem.isExportRestricted())
				.build();
		
		for (String num : cartonItem.getSerialNumber()) {
			lineItemCartonItem.addDespatchAdviceShipNoticeLineItemCartonItemSerialNumber(
					DespatchAdviceShipNoticeLineItemCartonItemSerialNumber.builder().serialNumber(num).build());
		}
		
		return lineItemCartonItem;
	}
	
	private String buildWeight(String type, String measure, List<Weight> lstWeight)
	{
		for(Weight weight:lstWeight) {
			if(measure.equals(weight.getUnitOfMeasure()) && type.equals(weight.getType()))
				return weight.getText();
		}
		return "";
	}

	private String buildLength(String measure, List<Length> lstLength)
	{
		for(Length length:lstLength) {
			if(measure.equals(length.getUnitOfMeasure()))
				return length.getText();
		}
		return "";
	}

	private String buildWidth(String measure, List<Width> lstWidth)
	{
		for(Width width:lstWidth) {
			if(measure.equals(width.getUnitOfMeasure()))
				return width.getText();
		}
		return "";
	}
	
	private String buildHeight(String measure, List<Height> lstHeight)
	{
		for(Height height:lstHeight) {
			if(measure.equals(height.getUnitOfMeasure()))
				return height.getText();
		}
		return "";
	}
	
}
