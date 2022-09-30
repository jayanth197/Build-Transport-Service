/**
 * 
 */
package com.cintap.transport.global.parser;

import org.springframework.stereotype.Service;

import com.cintap.transport.global.model.AT5SegmentInfo;
import com.cintap.transport.global.model.AT8SegmentInfo;
import com.cintap.transport.global.model.B2ASegmentInfo;
import com.cintap.transport.global.model.B2SegmentInfo;
import com.cintap.transport.global.model.EDI204GlobalMessage;
import com.cintap.transport.global.model.G61SegmentInfo;
import com.cintap.transport.global.model.G62SegmentInfo;
import com.cintap.transport.global.model.GESegmentInfo;
import com.cintap.transport.global.model.GSSegmentInfo;
import com.cintap.transport.global.model.IEASegmentInfo;
import com.cintap.transport.global.model.ISASegmentInfo;
import com.cintap.transport.global.model.L11SegmentInfo;
import com.cintap.transport.global.model.L3SegmentInfo;
import com.cintap.transport.global.model.L5SegmentInfo;
import com.cintap.transport.global.model.LADSegmentInfo;
import com.cintap.transport.global.model.LFHSegmentInfo;
import com.cintap.transport.global.model.LH1SegmentInfo;
import com.cintap.transport.global.model.LH2SegmentInfo;
import com.cintap.transport.global.model.LH3SegmentInfo;
import com.cintap.transport.global.model.M7SegmentInfo;
import com.cintap.transport.global.model.MEASegmentInfo;
import com.cintap.transport.global.model.MS3SegmentInfo;
import com.cintap.transport.global.model.N1SegmentInfo;
import com.cintap.transport.global.model.N2SegmentInfo;
import com.cintap.transport.global.model.N3SegmentInfo;
import com.cintap.transport.global.model.N4SegmentInfo;
import com.cintap.transport.global.model.N7SegmentInfo;
import com.cintap.transport.global.model.NTESegmentInfo;
import com.cintap.transport.global.model.OIDSegmentInfo;
import com.cintap.transport.global.model.PLDSegmentInfo;
import com.cintap.transport.global.model.S5SegmentInfo;
import com.cintap.transport.global.model.SESegmentInfo;
import com.cintap.transport.global.model.STSegmentInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class GlobalSegmentParser {
	
	public AT5SegmentInfo parseAT5Segment(String line) {
		String[] fields = getFields(line);
		AT5SegmentInfo obj = new AT5SegmentInfo();
		obj.setSpecialHandlingCode(fields.length > 1 ? fields[1] : null);
		obj.setSpecialServicesCode(fields.length > 2 ? fields[2] : null);
		obj.setSpecialHandlingDescription(fields.length > 3 ? fields[3] : null);
		
		log.info("AT5SegmentInfo is : "+obj);
		return obj;
	}
	
	public AT8SegmentInfo parseAT8Segment(String line) {
		String[] fields = getFields(line);
		AT8SegmentInfo obj = new AT8SegmentInfo();
		obj.setWeightQualifier(fields.length > 1 ? fields[1] : null);
		obj.setWeightUnitCode(fields.length > 2 ? fields[2] : null);
		obj.setWeight(fields.length > 3 ? fields[3] : null);
		obj.setLadingQuantity01(fields.length > 4 ? fields[4] : null);
		obj.setLadingQuantity02(fields.length > 5 ? fields[5] : null);
		obj.setVolumeUnitQualifier(fields.length > 6 ? fields[6] : null);
		obj.setVolume(fields.length > 7 ? fields[7] : null);
		
		log.info("AT8SegmentInfo is : "+obj);
		return obj;
	}
	
	public EDI204GlobalMessage parseB2ASegment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		B2ASegmentInfo obj = new B2ASegmentInfo();
		obj.setTransactionSetPurposeCode(fields.length > 1 ? fields[1] : null);
		obj.setApplicationType(fields.length > 2 ? fields[2] : null);
		log.info("B2ASegmentInfo is : "+obj);
		msg.getHeaderSegmentInfo().setB2aSegmentInfo(obj);
		return msg;
	}
	
	public EDI204GlobalMessage parseB2Segment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		B2SegmentInfo obj = new B2SegmentInfo();
		obj.setTarriffServiceCode(fields.length > 1 ? fields[1] : null);
		obj.setStandardCarrierAlphaCode(fields.length > 2 ? fields[2] : null);
		obj.setShipmentIdentificationNumber(fields.length > 4 ? fields[4] : null);
		obj.setWeightUnitCode(fields.length > 5 ? fields[5] : null);
		obj.setShipmentMethodOfPayment(fields.length > 6 ? fields[6] : null);
		obj.setShipmentQualifier(fields.length > 7 ? fields[7] : null);
		obj.setTotalEquipment(fields.length > 8 ? fields[8] : null);
		obj.setShipmentWeightCode(fields.length > 9 ? fields[9] : null);
		obj.setCustomsDocumentationHandlingCode(fields.length > 10 ? fields[10] : null);
		obj.setTransportationTermsCode(fields.length > 11 ? fields[12] : null);
		obj.setPaymentMethodCode(fields.length > 1 ? fields[1] : null);
		log.info("B2SegmentInfo is : "+obj);
		msg.getHeaderSegmentInfo().setB2SegmentInfo(obj);
		return msg;
	}
	
	public G61SegmentInfo parseG61Segment(String line) {
		String[] fields = getFields(line);
		G61SegmentInfo obj = new G61SegmentInfo();
		obj.setContactFunctionCode(fields.length > 1 ? fields[1] : null);
		obj.setName(fields.length > 2 ? fields[2] : null);
		obj.setCommunicationNumberQualifier(fields.length > 3 ? fields[3] : null);
		obj.setCommunicationNumber(fields.length > 4 ? fields[4] : null);
		log.info("G61SegmentInfo is : "+obj);
		return obj;
	}
	
	public G62SegmentInfo parseG62Segment(String line) {
		String[] fields = getFields(line);
		G62SegmentInfo obj = new G62SegmentInfo();
		obj.setDateQualifier(fields.length > 1 ? fields[1] : null);
		obj.setDate(fields.length > 2 ? fields[2] : null);
		obj.setTimeQualifier(fields.length > 3 ? fields[3] : null);
		obj.setTime(fields.length > 4 ? fields[4] : null);
		log.info("G62SegmentInfo is : "+obj);
		return obj;
	}
	
	public EDI204GlobalMessage parseGESegment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		GESegmentInfo obj = new GESegmentInfo();
		obj.setNumberOfTransactionSetsIncluded(fields.length > 1 ? fields[1] : null);
		obj.setGroupControlNumber(fields.length > 2 ? fields[2] : null);
		log.info("GESegmentInfo is : "+obj);
		msg.getEndSegmentInfo().setGeSegmentInfo(obj);
		return msg;
	}
	
	public EDI204GlobalMessage parseGSSegment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		GSSegmentInfo gsStartSegment = new GSSegmentInfo();
		gsStartSegment.setGs01FunctionalIdentifierCode(fields.length > 1 ? fields[1] : null);
		gsStartSegment.setGs02ApplicationSenderCode(fields.length > 2 ? fields[2] : null);
		gsStartSegment.setGs03ApplicationReceiverCode(fields.length > 3 ? fields[3] : null);
		gsStartSegment.setGs04Date(fields.length > 4 ? fields[4] : null);
		gsStartSegment.setGs05Time(fields.length > 5 ? fields[5] : null);
		gsStartSegment.setGs06GroupControlNumber(fields.length > 6 ? fields[6] : null);
		gsStartSegment.setGs07ResponsibleAgencyCode(fields.length > 7 ? fields[7] : null);
		gsStartSegment.setGs08Version(fields.length > 8 ? fields[8] : null);
		log.info("GSSegmentInfo is : "+gsStartSegment);
		msg.getStarterSegentInfo().setGsSegmentInfo(gsStartSegment);
		return msg;
	}
	
	public EDI204GlobalMessage parseIEASegment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		IEASegmentInfo obj = new IEASegmentInfo();
		obj.setNumberOfIncludedFunctionalGroups(fields.length > 1 ? fields[1] : null);
		obj.setInterchangeControlNumber(fields.length > 2 ? fields[2] : null);
		log.info("IEASegmentInfo is : "+obj);
		msg.getEndSegmentInfo().setIeaSegmentInfo(obj);
		return msg;
	}
	
	public EDI204GlobalMessage parseISASegment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		ISASegmentInfo isaStartMessageHeader = new ISASegmentInfo();
		isaStartMessageHeader.setField1(fields.length > 1 ? fields[1] : null);
		isaStartMessageHeader.setField2(fields.length > 2 ? fields[2] : null);
		isaStartMessageHeader.setField3(fields.length > 3 ? fields[3] : null);
		isaStartMessageHeader.setField4(fields.length > 4 ? fields[4] : null);
		isaStartMessageHeader.setField5(fields.length > 5 ? fields[5] : null);
		isaStartMessageHeader.setField6(fields.length > 6 ? fields[6] : null);
		isaStartMessageHeader.setField7(fields.length > 7 ? fields[7] : null);
		isaStartMessageHeader.setField8(fields.length > 8 ? fields[8] : null);
		isaStartMessageHeader.setField9(fields.length > 9 ? fields[9] : null);
		isaStartMessageHeader.setField10(fields.length > 10 ? fields[10] : null);
		isaStartMessageHeader.setField11(fields.length > 11 ? fields[11] : null);
		isaStartMessageHeader.setField12(fields.length > 12 ? fields[12] : null);
		isaStartMessageHeader.setField13(fields.length > 13 ? fields[13] : null);
		isaStartMessageHeader.setField14(fields.length > 14 ? fields[14] : null);
		isaStartMessageHeader.setField15(fields.length > 15 ? fields[15] : null);
		isaStartMessageHeader.setField16(fields.length > 16 ? fields[16] : null);
		log.info("ISASegmentInfo is : "+isaStartMessageHeader);
		msg.getStarterSegentInfo().setIsaSegmentInfo(isaStartMessageHeader);
		return msg;
	}
	
	public L11SegmentInfo parseL11Segment(String line) {
		String[] fields = getFields(line);
		L11SegmentInfo obj = new L11SegmentInfo();
		obj.setReferenceIdentification(fields.length > 1 ? fields[1] : null);
		obj.setReferenceIdentificationQualifier(fields.length > 2 ? fields[2] : null);
		obj.setDescription(fields.length > 3 ? fields[3] : null);
		log.info("L11SegmentInfo is : "+obj);
		return obj;
	}
	
	public L3SegmentInfo parseL3Segment(String line) {
		String[] fields = getFields(line);
		L3SegmentInfo obj = new L3SegmentInfo();
		obj.setWeight(fields.length > 1 ? fields[1] : null);
		obj.setWeightQualifier(fields.length > 2 ? fields[2] : null);
		obj.setCharge(fields.length > 5 ? fields[5] : null);
		obj.setVolume(fields.length > 9 ? fields[9] : null);
		obj.setVolumeUnitQualifier(fields.length > 10 ? fields[10] : null);
		obj.setLadingQuantity(fields.length > 11 ? fields[11] : null);
		obj.setWeightUnitCode(fields.length > 12 ? fields[12] : null);
		log.info("L3SegmentInfo is : "+obj);
		return obj;
	}
	
	public L5SegmentInfo parseL5Segment(String line) {
		String[] fields = getFields(line);
		L5SegmentInfo obj = new L5SegmentInfo();
		obj.setLadingLineItemNumber(fields.length > 1 ? fields[1] : null);
		obj.setLadingDescription(fields.length > 2 ? fields[2] : null);
		obj.setMarksAndNumbers(fields.length > 6 ? fields[6] : null);
		obj.setCommodityCodeQualifier(fields.length > 8 ? fields[8] : null);
		obj.setCommodityCode(fields.length > 9 ? fields[9] : null);
		log.info("L5SegmentInfo is : "+obj);
		return obj;
	}
	
	public LADSegmentInfo parseLADSegment(String line) {
		String[] fields = getFields(line);
		LADSegmentInfo obj = new LADSegmentInfo();
		obj.setPackagingFormCode(fields.length > 1 ? fields[1] : null);
		obj.setLadingQuantity(fields.length > 2 ? fields[2] : null);
		obj.setWeightUnitCode(fields.length > 3 ? fields[3] : null);
		obj.setUnitWeight(fields.length > 4 ? fields[4] : null);
		obj.setProductOrServiceIDQualifier01(fields.length > 7 ? fields[7] : null);
		obj.setProductOrServiceID01(fields.length > 8 ? fields[8] : null);
		obj.setProductOrServiceIDQualifier02(fields.length > 9 ? fields[9] : null);
		obj.setProductOrServiceID02(fields.length > 10 ? fields[10] : null);
		log.info("LADSegmentInfo is : "+obj);
		return obj;
	}
	
	public LFHSegmentInfo parseLFHSegment(String line) {
		String[] fields = getFields(line);
		LFHSegmentInfo obj = new LFHSegmentInfo();
		obj.setHazardousMaterialShipmentInformationQualifier(fields.length > 1 ? fields[1] : null);
		obj.setHzardousMaterialShipmentInformation01(fields.length > 2 ? fields[2] : null);
		obj.setHazardousMaterialShipmentInformation02(fields.length > 3 ? fields[3] : null);
		obj.setHazardZoneCode(fields.length > 4 ? fields[4] : null);
		log.info("LFHSegmentInfo is : "+obj);
		return obj;
	}
	
	public LH1SegmentInfo parseLH1Segment(String line) {
		String[] fields = getFields(line);
		LH1SegmentInfo obj = new LH1SegmentInfo();
		obj.setUnitOrBasisForMeasurementCode(fields.length > 1 ? fields[1] : null);
		obj.setLadingQuantity(fields.length > 2 ? fields[2] : null);
		obj.setUnOrNAIdentificationCode(fields.length > 3 ? fields[3] : null);
		obj.setPackingGroupCode(fields.length > 10 ? fields[10] : null);
		log.info("LH1SegmentInfo is : "+obj);
		return obj;
	}
	
	public LH2SegmentInfo parseLH2Segment(String line) {
		String[] fields = getFields(line);
		LH2SegmentInfo obj = new LH2SegmentInfo();
		obj.setHazardousClassification(fields.length > 1 ? fields[1] : null);
		obj.setHazardousClassQualifier(fields.length > 2 ? fields[2] : null);
		obj.setHazardousPlacardNotation(fields.length > 3 ? fields[3] : null);
		obj.setReportableQuantityCode(fields.length > 5 ? fields[5] : null);
		log.info("LH2SegmentInfo is : "+obj);
		return obj;
	}
	
	public LH3SegmentInfo parseLH3Segment(String line) {
		String[] fields = getFields(line);
		LH3SegmentInfo obj = new LH3SegmentInfo();
		obj.setHazardousMaterialShippingName(fields.length > 1 ? fields[1] : null);
		obj.setHazardousMaterialShippingNameQualifier(fields.length > 2 ? fields[2] : null);
		obj.setNosIndicatorCode(fields.length > 3 ? fields[3] : null);
		log.info("LH3SegmentInfo is : "+obj);
		return obj;
	}
	
	public M7SegmentInfo parseM7Segment(String line) {
		String[] fields = getFields(line);
		M7SegmentInfo obj = new M7SegmentInfo();
		obj.setSealNumber01(fields.length > 1 ? fields[1] : null);
		obj.setSealNumber02(fields.length > 2 ? fields[2] : null);
		obj.setSealNumber03(fields.length > 3 ? fields[3] : null);
		obj.setSealNumber04(fields.length > 4 ? fields[4] : null);
		obj.setEntityIdentifierCode(fields.length > 5 ? fields[5] : null);
		log.info("M7SegmentInfo is : "+obj);
		return obj;
	}
	
	public MEASegmentInfo parseMEASegment(String line) {
		String[] fields = getFields(line);
		MEASegmentInfo obj = new MEASegmentInfo();
		obj.setMeasurementReferenceIDCode(fields.length > 1 ? fields[1] : null);
		obj.setMeasurementQualifier(fields.length > 2 ? fields[2] : null);
		obj.setMeasurementValue(fields.length > 3 ? fields[3] : null);
		obj.setCompositeUnitOfMeasure(fields.length > 4 ? fields[4] : null);
		obj.setUnitOrBasisForMeasurementCode(fields.length > 4 ? fields[4] : null);
		obj.setExponent(fields.length > 4 ? fields[4] : null);
		obj.setMultiplier(fields.length > 4 ? fields[4] : null);
		log.info("MEASegmentInfo is : "+obj);
		return obj;
	}
	
	public EDI204GlobalMessage parseMS3Segment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		MS3SegmentInfo obj = new MS3SegmentInfo();
		obj.setStandardCarrierAlphaCode(fields.length > 1 ? fields[1] : null);
		obj.setRoutingSequenceCode(fields.length > 2 ? fields[2] : null);
		obj.setCityName(fields.length > 3 ? fields[3] : null);
		obj.setTransportationMethodOrTypeCode(fields.length > 4 ? fields[4] : null);
		obj.setStateOrProvinceCode(fields.length > 5 ? fields[5] : null);
		log.info("MS3SegmentInfo is : "+obj);
		msg.getHeaderSegmentInfo().setMs3SegmentInfo(obj);
		return msg;
	}
	
	public N1SegmentInfo parseN1Segment(String line) {
		String[] fields = getFields(line);
		N1SegmentInfo obj = new N1SegmentInfo();
		obj.setEntityIdentifierCode(fields.length > 1 ? fields[1] : null);
		obj.setName(fields.length > 2 ? fields[2] : null);
		obj.setIdentificationCodeQualifier(fields.length > 3 ? fields[3] : null);
		obj.setIdentificationCode(fields.length > 4 ? fields[4] : null);
		obj.setEntityRelationshipCode(fields.length > 5 ? fields[5] : null);
		obj.setEntityIdentifierCode(fields.length > 6 ? fields[6] : null);
		log.info("N1SegmentInfo is : "+obj);
		return obj;
	}
	
	public N2SegmentInfo parseN2Segment(String line) {
		String[] fields = getFields(line);
		N2SegmentInfo obj = new N2SegmentInfo();
		obj.setName1(fields.length > 1 ? fields[1] : null);
		obj.setName2(fields.length > 2 ? fields[2] : null);
		log.info("N2SegmentInfo is : "+obj);
		return obj;
	}
	
	public N3SegmentInfo parseN3Segment(String line) {
		String[] fields = getFields(line);
		N3SegmentInfo obj = new N3SegmentInfo();
		obj.setAddressInformation1(fields.length > 1 ? fields[1] : null);
		obj.setAddressInformation2(fields.length > 2 ? fields[2] : null);
		log.info("N3SegmentInfo is : "+obj);
		return obj;
	}
	
	public N4SegmentInfo parseN4Segment(String line) {
		String[] fields = getFields(line);
		N4SegmentInfo obj = new N4SegmentInfo();
		obj.setCityName(fields.length > 1 ? fields[1] : null);
		obj.setStateOrProvinceCode(fields.length > 2 ? fields[2] : null);
		obj.setPostalCode(fields.length > 3 ? fields[3] : null);
		obj.setCountryCode(fields.length > 4 ? fields[4] : null);
		obj.setLocationQualifier(fields.length > 5 ? fields[5] : null);
		obj.setLocationIdentifier(fields.length > 6 ? fields[6] : null);
		log.info("N4SegmentInfo is : "+obj);
		return obj;
	}
	
	public N7SegmentInfo parseN7Segment(String line) {
		String[] fields = getFields(line);
		N7SegmentInfo obj = new N7SegmentInfo();
		obj.setEquipmentInitial(fields.length > 1 ? fields[1] : null);
		obj.setEquipmentNumber(fields.length > 2 ? fields[2] : null);
		obj.setEquipmentDescriptionCode(fields.length > 11 ? fields[11] : null);
		obj.setEquipmentType(fields.length > 22 ? fields[22] : null);
		log.info("N7SegmentInfo is : "+obj);
		return obj;
	}
	
	public NTESegmentInfo parseNTESegment(String line) {
		String[] fields = getFields(line);
		NTESegmentInfo obj = new NTESegmentInfo();
		obj.setNoteReferenceCode(fields.length > 1 ? fields[1] : null);
		obj.setDescription(fields.length > 2 ? fields[2] : null);
		log.info("NTESegmentInfo is : "+obj);
		return obj;
	}
	
	public OIDSegmentInfo parseOIDSegment(String line) {
		String[] fields = getFields(line);
		OIDSegmentInfo obj = new OIDSegmentInfo();
		obj.setReferenceIdentification01(fields.length > 1 ? fields[1] : null);
		obj.setPurchaseOrderNumber(fields.length > 2 ? fields[2] : null);
		obj.setReferenceIdentification02(fields.length > 3 ? fields[3] : null);
		obj.setUnitOrBasisForMeasurementCode(fields.length > 4 ? fields[4] : null);
		obj.setQuantity(fields.length > 5 ? fields[5] : null);
		obj.setWeightUnitCode(fields.length > 6 ? fields[6] : null);
		obj.setWeight(fields.length > 7 ? fields[7] : null);
		obj.setVolumeUnitQualifier(fields.length > 8 ? fields[8] : null);
		obj.setVolume(fields.length > 9 ? fields[9] : null);
		log.info("OIDSegmentInfo is : "+obj);
		return obj;
	}
	
	public PLDSegmentInfo parsePLDSegment(String line) {
		String[] fields = getFields(line);
		PLDSegmentInfo obj = new PLDSegmentInfo();
		obj.setQuantityOfPalletsShipped(fields.length > 1 ? fields[1] : null);
		obj.setPalletExchangeCode(fields.length > 2 ? fields[2] : null);
		obj.setWeightUnitCode(fields.length > 3 ? fields[3] : null);
		obj.setWeight(fields.length > 4 ? fields[4] : null);
		
		log.info("PLDSegmentInfo is : "+obj);
		return obj;
	}
	
	public S5SegmentInfo parseS5Segment(String line) {
		String[] fields = getFields(line);
		S5SegmentInfo obj = new S5SegmentInfo();
		obj.setStopSequenceNumber(fields.length > 1 ? fields[1] : null);
		obj.setStopReasonCode(fields.length > 2 ? fields[2] : null);
		obj.setWeight(fields.length > 3 ? fields[3] : null);
		obj.setWeightUnitCode(fields.length > 4 ? fields[4] : null);
		obj.setNumberOfUnitsShipped(fields.length > 5 ? fields[5] : null);
		obj.setUnitOrBasisForMeasurementCode(fields.length > 6 ? fields[6] : null);
		obj.setVolume(fields.length > 7 ? fields[7] : null);
		obj.setVolumeUnitQualifier(fields.length > 8 ? fields[8] : null);
		obj.setDescription01(fields.length > 9 ? fields[9] : null);
		obj.setDescription02(fields.length > 10 ? fields[10] : null);
		obj.setDescription03(fields.length > 11 ? fields[11] : null);
		log.info("S5SegmentInfo is : "+obj);
		return obj;
	}
	
	public EDI204GlobalMessage parseSESegment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		SESegmentInfo obj = new SESegmentInfo();
		obj.setNumberOfIncludedSegments(fields.length > 1 ? fields[1] : null);
		obj.setTransactionSetControlNumbe(fields.length > 2 ? fields[2] : null);
		log.info("SESegmentInfo is : "+obj);
		msg.getEndSegmentInfo().setSegmentInfo(obj);
		return msg;
	}
	
	public EDI204GlobalMessage parseSTSegment(String line, EDI204GlobalMessage msg) {
		String[] fields = getFields(line);
		STSegmentInfo obj = new STSegmentInfo();
		obj.setSt01IdentifierCode(fields.length > 1 ? fields[1] : null);
		obj.setSt02ControlNumber(fields.length > 2 ? fields[2] : null);
		log.info("SegmentInfo is : "+obj);
		msg.getHeaderSegmentInfo().setStSegmentInfo(obj);
		return msg;
	}
	
	private String[] getFields(String line) {
		return line.split("\\*");
	}
}
