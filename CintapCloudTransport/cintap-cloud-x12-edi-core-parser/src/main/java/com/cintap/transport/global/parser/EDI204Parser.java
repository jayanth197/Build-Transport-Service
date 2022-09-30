/**
 * 
 */
package com.cintap.transport.global.parser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cintap.transport.global.model.AT5SegmentInfo;
import com.cintap.transport.global.model.AT8SegmentInfo;
import com.cintap.transport.global.model.CommunicationInfo;
import com.cintap.transport.global.model.EDI204GlobalMessage;
import com.cintap.transport.global.model.EquipementInfo;
import com.cintap.transport.global.model.G61SegmentInfo;
import com.cintap.transport.global.model.G62SegmentInfo;
import com.cintap.transport.global.model.HazardousInfo;
import com.cintap.transport.global.model.L11SegmentInfo;
import com.cintap.transport.global.model.L3SegmentInfo;
import com.cintap.transport.global.model.L5SegmentInfo;
import com.cintap.transport.global.model.LADSegmentInfo;
import com.cintap.transport.global.model.LFHSegmentInfo;
import com.cintap.transport.global.model.LH1SegmentInfo;
import com.cintap.transport.global.model.LH2SegmentInfo;
import com.cintap.transport.global.model.LH3SegmentInfo;
import com.cintap.transport.global.model.LineItemInfo;
import com.cintap.transport.global.model.M7SegmentInfo;
import com.cintap.transport.global.model.MEASegmentInfo;
import com.cintap.transport.global.model.N1SegmentInfo;
import com.cintap.transport.global.model.N2SegmentInfo;
import com.cintap.transport.global.model.N3SegmentInfo;
import com.cintap.transport.global.model.N4SegmentInfo;
import com.cintap.transport.global.model.N7SegmentInfo;
import com.cintap.transport.global.model.NTESegmentInfo;
import com.cintap.transport.global.model.NameInfo;
import com.cintap.transport.global.model.OIDSegmentInfo;
import com.cintap.transport.global.model.OrderInfo;
import com.cintap.transport.global.model.PLDSegmentInfo;
import com.cintap.transport.global.model.S5DetailInfo;
import com.cintap.transport.global.model.S5SegmentInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class EDI204Parser {

	@Autowired
	private GlobalSegmentParser segmentParser = new GlobalSegmentParser();

	public void parseEDI204Message(List<String> fileLines) {
		EDI204GlobalMessage msg = new EDI204GlobalMessage();
		NameInfo nameInfo = null;
		boolean isB2AsegemtVisited = false;
		boolean isS5SegmentVisited = false;
		S5DetailInfo s5DetailInfo = null;
		boolean isS5G62Segment1Visited = false;
		boolean isDetailPartVisited = false;
		EquipementInfo equipementInfo = null;
		boolean isL5SegmentVisisted = false;
		LineItemInfo lineItemInfo = null;
		CommunicationInfo communicationInfo = null;
		boolean isLADSegmentVisited = false;
		HazardousInfo hazardousInfo = null;
		OrderInfo orderInfo = null;
		int count = 0;
		for (String line : fileLines) {
			count++;
			log.info("" + line);
			String[] lineFields = line.split("\\*");
			String segmentName = lineFields.length > 1 ? lineFields[0] : null;
			System.out.println("" + segmentName);

			switch (segmentName) {
			case "ISA":
				msg = segmentParser.parseISASegment(line, msg);
				break;
			case "GS":
				msg = segmentParser.parseGSSegment(line, msg);
				break;
			case "ST":
				msg = segmentParser.parseSTSegment(line, msg);
				break;
			case "B2":
				msg = segmentParser.parseB2Segment(line, msg);
				break;
			case "B2A":
				msg = segmentParser.parseB2ASegment(line, msg);
				isB2AsegemtVisited = true;
				break;
			case "L11":
				L11SegmentInfo l11SegmentInfo = segmentParser.parseL11Segment(line);
				if(isLADSegmentVisited) {
					s5DetailInfo.addToL11SegmentInfoList(l11SegmentInfo);
				}else if(isL5SegmentVisisted) {
					communicationInfo.addToL11SegmentInfoList(l11SegmentInfo);
				}else if(isS5SegmentVisited) {
					s5DetailInfo.addToL11SegmentInfoList1(l11SegmentInfo);
				}else if(isB2AsegemtVisited) {
					msg.getHeaderSegmentInfo().addToL11SegmentInfoList(l11SegmentInfo);
				} 
				break;
			case "G62":
				G62SegmentInfo g62SegmentInfo = segmentParser.parseG62Segment(line);
				if(isB2AsegemtVisited){
					msg.getHeaderSegmentInfo().addToG62SegmentInfoList(g62SegmentInfo);
				}else if(isS5SegmentVisited) {
					if(!isS5G62Segment1Visited) {
						s5DetailInfo.setG62SegmentInfo1(g62SegmentInfo);
						isS5G62Segment1Visited = true;
					}else {
						s5DetailInfo.setG62SegmentInfo2(g62SegmentInfo);
					}
				}
				break;
			case "MS3":
				msg = segmentParser.parseMS3Segment(line, msg);
				isB2AsegemtVisited = false;
				break;
			case "AT5":
				AT5SegmentInfo at5SegmentInfo = segmentParser.parseAT5Segment(line);
				if(isDetailPartVisited) {
					s5DetailInfo.addToAT5SegmentInfoList(at5SegmentInfo);
				}else {
					msg.getHeaderSegmentInfo().addToAT5SegmentInfoList(at5SegmentInfo);
				}
				break;
			case "PLD":
				PLDSegmentInfo pldSegmentInfo = segmentParser.parsePLDSegment(line);
				if(isDetailPartVisited) {
					s5DetailInfo.setPldSegmentInfo(pldSegmentInfo);
				}else {
					msg.getHeaderSegmentInfo().setPldSegmentInfo(pldSegmentInfo);
				}
				break;
			case "NTE":
				NTESegmentInfo nteSegmentInfo = segmentParser.parseNTESegment(line);
				if(isDetailPartVisited) {
					s5DetailInfo.addToNTESegmentInfoList(nteSegmentInfo);
				}else {
					msg.getHeaderSegmentInfo().addToNTESegmentInfoList(nteSegmentInfo);
				}
				break;
			case "N1":
				if(null != nameInfo) {
					if(isDetailPartVisited) {
						s5DetailInfo.addToNameInfoList(nameInfo);
					}else {
						msg.getHeaderSegmentInfo().addToNameInfoList(nameInfo);
					}
				}
				nameInfo = new NameInfo();
				N1SegmentInfo n1SegmentInfo = segmentParser.parseN1Segment(line);
				nameInfo.setN1SegmentInfo(n1SegmentInfo);
				
				
				break;
			case "N2":
				N2SegmentInfo n2SegmentInfo = segmentParser.parseN2Segment(line);
				nameInfo.setN2SegmentInfo(n2SegmentInfo);
				break;
			case "N3":
				N3SegmentInfo n3SegmentInfo = segmentParser.parseN3Segment(line);
				nameInfo.addTonNSegmentInfo(n3SegmentInfo);
				
				break;
			case "N4":
				N4SegmentInfo n4SegmentInfo = segmentParser.parseN4Segment(line);
				nameInfo.setN4SegmentInfo(n4SegmentInfo);
				break;
			case "G61":
				G61SegmentInfo g61SegmentInfo = segmentParser.parseG61Segment(line);
				if(isL5SegmentVisisted) {
					if(null != communicationInfo) {
						s5DetailInfo.addTocommunicationInfoList(communicationInfo);
					}
					communicationInfo = new CommunicationInfo();
					communicationInfo.setG61SegmentInfo(g61SegmentInfo);
				}else {
					nameInfo.addToG61SegmentInfoList(g61SegmentInfo);
				}
				break;
			case "N7":
				if(null != equipementInfo) {
					msg.getHeaderSegmentInfo().addToEquipementInfoList(equipementInfo);
				}
				equipementInfo = new EquipementInfo();
				N7SegmentInfo n7SegmentInfo = segmentParser.parseN7Segment(line);
				equipementInfo.setN7SegmentInfo(n7SegmentInfo);
				if(null != nameInfo) {
					System.out.println("cALLING at line number : "+count);
					msg.getHeaderSegmentInfo().addToNameInfoList(nameInfo);
					nameInfo = null;
				}
				break;
			case "MEA":
				MEASegmentInfo meaSegmentInfo = segmentParser.parseMEASegment(line);
				equipementInfo.setMeaSegmentInfo(meaSegmentInfo);
				break;
			case "M7":
				M7SegmentInfo m7SegmentInfo = segmentParser.parseM7Segment(line);
				equipementInfo.addToM7SegmentInfo(m7SegmentInfo);
				break;
			case "S5":
				isL5SegmentVisisted = false;
				isLADSegmentVisited = false;
				isS5G62Segment1Visited = false;
				if(null != nameInfo && null != s5DetailInfo) {
					s5DetailInfo.addToNameInfoList(nameInfo);
					nameInfo = null;
				}
				if(null != communicationInfo) {
					s5DetailInfo.addTocommunicationInfoList(communicationInfo);
				}
				if(null != lineItemInfo) {
					s5DetailInfo.addTolineItemInfoList(lineItemInfo);
				}
				if(null != orderInfo) {
					s5DetailInfo.addToOrderInfoList(orderInfo);
				}
				if(null != equipementInfo) {
					msg.getHeaderSegmentInfo().addToEquipementInfoList(equipementInfo);
					equipementInfo = null;
				}
				if(null != s5DetailInfo) {
					msg.getDetailInfo().addTos5DetailInfoList(s5DetailInfo);
				}
				s5DetailInfo = new S5DetailInfo();
				nameInfo = null;
				lineItemInfo = null;
				communicationInfo = null;
				orderInfo = null;
				hazardousInfo = null;
				S5SegmentInfo s5SegmentInfo = segmentParser.parseS5Segment(line);
				s5DetailInfo.setS5SegmentInfo(s5SegmentInfo);
				isS5SegmentVisited = true;
				isDetailPartVisited = true;
				break;
			case "AT8":
				AT8SegmentInfo at8SegmentInfo = segmentParser.parseAT8Segment(line);
				s5DetailInfo.setAt8SegmentInfo(at8SegmentInfo);
				isS5SegmentVisited = false;
				break;
			case "L5":
				if(null != lineItemInfo) {
					s5DetailInfo.addTolineItemInfoList(lineItemInfo);
				}
				lineItemInfo = new LineItemInfo();
				L5SegmentInfo l5SegmentInfo = segmentParser.parseL5Segment(line);
				lineItemInfo.setL5SegmentInfo(l5SegmentInfo);
				isL5SegmentVisisted = true;
				/*
				 * if(null != nameInfo) { s5DetailInfo.addToNameInfoList(nameInfo); }
				 */
				break;
			case "LH1":
				if(null != hazardousInfo) {
					s5DetailInfo.addToHazardousInfoList(hazardousInfo);
				}
				hazardousInfo = new HazardousInfo();
				LH1SegmentInfo lh1SegmentInfo = segmentParser.parseLH1Segment(line);
				hazardousInfo.setLh1SegmentInfo(lh1SegmentInfo);
				
				break;
			case "LH2":
				LH2SegmentInfo lh2SegmentInfo = segmentParser.parseLH2Segment(line);
				hazardousInfo.addToLH2SegmentInfoList(lh2SegmentInfo);
				break;
			case "LH3":
				LH3SegmentInfo lh3SegmentInfo = segmentParser.parseLH3Segment(line);
				hazardousInfo.addTolh3SegmentInfoList(lh3SegmentInfo);
				break;
			case "LFH":
				LFHSegmentInfo lfhSegmentInfo = segmentParser.parseLFHSegment(line);
				hazardousInfo.addTolfhSegmentInfoList(lfhSegmentInfo);
				break;
			case "OID":
				if(null != orderInfo) {
					s5DetailInfo.addToOrderInfoList(orderInfo);
				}
				orderInfo = new OrderInfo();
				OIDSegmentInfo oidSegmentInfo  = segmentParser.parseOIDSegment(line);
				orderInfo.setOidSegmentInfo(oidSegmentInfo);
				break;
			case "LAD":
				LADSegmentInfo ladSegmentInfo = segmentParser.parseLADSegment(line);
				orderInfo.addToladSegmentInfoList(ladSegmentInfo);
				isLADSegmentVisited = true;
				break;
			case "L3":
				L3SegmentInfo l3SegmentInfo = segmentParser.parseL3Segment(line);
				msg.getEndSegmentInfo().setL3SegmentInfo(l3SegmentInfo);
				
				break;
			case "SE":
				msg = segmentParser.parseSESegment(line, msg);
				if(null != communicationInfo) {
					s5DetailInfo.addTocommunicationInfoList(communicationInfo);
				}
				if(null != lineItemInfo) {
					s5DetailInfo.addTolineItemInfoList(lineItemInfo);
				}
				if(null != orderInfo) {
					s5DetailInfo.addToOrderInfoList(orderInfo);
				}
				if(null != s5DetailInfo) {
					msg.getDetailInfo().addTos5DetailInfoList(s5DetailInfo);
				}
				
				break;
			case "GE":
				msg = segmentParser.parseGESegment(line, msg);
				break;
			case "IEA":
				msg = segmentParser.parseIEASegment(line, msg);
				break;
				
				default :
					System.out.println("INVALID SEGMENT : "+segmentName);
			}
		}
		System.out.println("EDI204GlobalMessage is : "+msg);

	}
}
