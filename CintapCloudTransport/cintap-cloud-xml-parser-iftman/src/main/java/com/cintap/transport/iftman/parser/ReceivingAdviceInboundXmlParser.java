package com.cintap.transport.iftman.parser;

import java.util.List;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceHeader;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceReceipt;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceReceiptAddress;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceReceiptPieces;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceReceiptPiecesItem;
import com.cintap.transport.iftman.model.Height;
import com.cintap.transport.iftman.model.Interface;
import com.cintap.transport.iftman.model.Item;
import com.cintap.transport.iftman.model.Length;
import com.cintap.transport.iftman.model.Piece;
import com.cintap.transport.iftman.model.Receipt;
import com.cintap.transport.iftman.model.Warehouse;
import com.cintap.transport.iftman.model.Weight;
import com.cintap.transport.iftman.model.Width;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceivingAdviceInboundXmlParser {

	@Autowired
	ReceivingAdviceXmlParser receivingAdviceXmlParser;

	public ReceivingAdviceHeader convertXmlToEntity(String fileData, FileUploadParams fileUploadParams)
			throws Exception {
		try {
			log.info("DespatchAdviceShipNoticeInboundXmlParser - convertXmlToEntity : fileDate" + fileData);
			Interface inter = receivingAdviceXmlParser.convertXmlTo(fileData);

			if(null==inter) {
				log.info("DespatchAdviceShipNoticeInboundXmlParser - convertXmlToEntity : Got exception converting xml to model" );
				return null;
			}
		ReceivingAdviceHeader receivingAdviceHeader = ReceivingAdviceHeader.builder()
				.tenantId(inter.getHeader().getTenantID())
				.senderCode(inter.getHeader().getSenderCode())
				.receiverCode(inter.getHeader().getReceiverCode())
				.documentCreationDate(inter.getHeader().getDocumentCreation())
				.messageReference(inter.getHeader().getMessageReference())
				.documentName(inter.getDocument())
				.identification(inter.getReceipt().getIdentification())
				.build();
			receivingAdviceHeader.addReceivingAdviceReceipt(buildReceipt(inter.getReceipt()));
			return receivingAdviceHeader;
		} catch (Exception e) {
			log.info("DespatchAdviceShipNoticeInboundXmlParser - convertXmlToEntity : Got Error");
			e.printStackTrace();
			throw e;
		}
	}
	
	private ReceivingAdviceReceipt buildReceipt(Receipt receipt) {
		ReceivingAdviceReceipt receivingAdviceReceipt = ReceivingAdviceReceipt.builder()
				.identification(receipt.getIdentification())
				.status(receipt.getStatus())
				.internalId(receipt.getInternalId())
				.type(receipt.getType())
				.statedPalletCount(receipt.getStatedPalletCount())
				.statedCartonCount(receipt.getStatedCartonCount())
				.customerCode(receipt.getDetails().getCustomerCode())
				.vendorName(receipt.getDetails().getVendorCode().getName())
				.vendorCode(receipt.getDetails().getVendorCode().getText())
				.warehouseCode(receipt.getDetails().getWarehouseCode())
				.inboundCarrier(receipt.getDetails().getInboundCarrier())
				.invoiceDate(receipt.getDetails().getInvoiceDate())
				.shipper(receipt.getDetails().getShipper())
				.purchaseOrderNumber(receipt.getDetails().getPurchaseOrder())
				.receivedTime(receipt.getDetails().getReceivedTime())
				.finalizedTime(receipt.getDetails().getFinalizedTime())
				.remark(receipt.getDetails().getRemark())
				.build();
		receivingAdviceReceipt.addReceivingAdviceReceiptAddress(buildReceiptAddress(receipt.getDetails().getWarehouse()));
		for (Piece piece : receipt.getPieces().getPiece()) {
			receivingAdviceReceipt.addReceivingAdviceReceiptPieces(buildReceiptPieces(piece));
		}
		return receivingAdviceReceipt;
	}
	
	private ReceivingAdviceReceiptAddress buildReceiptAddress(Warehouse warehouse) {
		ReceivingAdviceReceiptAddress receivingAdviceReceiptAddress = ReceivingAdviceReceiptAddress.builder()
				.company(warehouse.getCompany())
				.addressLine1(warehouse.getAddressLine().get(0))
				.addressline2(warehouse.getAddressLine().get(1))
				.city(warehouse.getCity())
				.state(warehouse.getState())
				.postalCode(warehouse.getPostalCode())
				.country(warehouse.getCountry())
				.internalId(warehouse.getInternalId())
				.addressType("Warehouse")
				.build();
		return receivingAdviceReceiptAddress;
	}

	private ReceivingAdviceReceiptPieces buildReceiptPieces(Piece piece) {
			ReceivingAdviceReceiptPieces receivingAdviceReceiptPieces = ReceivingAdviceReceiptPieces.builder()
					.pieceNumber(piece.getPieceNumber())
					.pieceType(piece.getPieceType())
					.pieceSerial(piece.getPieceSerial())
					.cartonCount(piece.getCartonCount())
					.remark(piece.getRemark())
					.grossWeightKg(buildWeight("gross", "kg", piece.getWeight()))
					.grossWeightLb(buildWeight("gross", "lb", piece.getWeight()))
					.lengthCm(buildLength("cm",piece.getLength()))
					.lengthInch(buildLength("inch",piece.getLength()))
					.widthCm(buildWidth("cm",piece.getWidth()))
					.widthInch(buildWidth("inch",piece.getWidth()))
					.heightCm(buildHeight("cm",piece.getHeight()))
					.heightInch(buildHeight("inch",piece.getHeight()))
					.build();
			receivingAdviceReceiptPieces.addReceivingAdviceReceiptPiecesItem(buildReceiptPiecesItem(piece.getItems().getItem()));
		return receivingAdviceReceiptPieces;
	}

	private ReceivingAdviceReceiptPiecesItem buildReceiptPiecesItem(Item item) {
		ReceivingAdviceReceiptPiecesItem receivingAdviceReceiptPiecesItem = ReceivingAdviceReceiptPiecesItem.builder()
				.partNumber(item.getPartNumber())
				.lin(item.getLIN())
				.conditionCode(item.getConditionCode())
				.grossWeight(item.getGrossWeight())
				.totalQuantity(item.getTotalQty())
				.build();
		return receivingAdviceReceiptPiecesItem;
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
