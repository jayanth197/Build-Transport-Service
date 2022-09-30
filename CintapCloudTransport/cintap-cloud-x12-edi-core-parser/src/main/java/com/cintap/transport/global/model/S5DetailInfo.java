/**
 * 
 */
package com.cintap.transport.global.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SurenderMogiloju
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class S5DetailInfo {
	private S5SegmentInfo s5SegmentInfo;
	private List<L11SegmentInfo> l11SegmentInfoList1 = new ArrayList<>();
	private G62SegmentInfo g62SegmentInfo1;
	private G62SegmentInfo g62SegmentInfo2;
	private AT8SegmentInfo at8SegmentInfo;
	private List<AT5SegmentInfo> at5SegmentInfoList = new ArrayList<>();
	private PLDSegmentInfo pldSegmentInfo;
	private List<NTESegmentInfo> nteSegmentInfoList = new ArrayList<>();
	private List<NameInfo> nameInfoList = new ArrayList<>();
	private List<LineItemInfo> lineItemInfoList = new ArrayList<>();
	private List<CommunicationInfo> communicationInfoList = new ArrayList<>();
	private List<HazardousInfo> hazardousInfoList = new ArrayList<>();
	private List<OrderInfo> OrderInfoList = new ArrayList<OrderInfo>();
	private List<L11SegmentInfo> l11SegmentInfoList = new ArrayList<>();
	
	public void addToL11SegmentInfoList1(L11SegmentInfo l11SegmentInfo) {
		l11SegmentInfoList1.add(l11SegmentInfo);
	}
	
	public void addToAT5SegmentInfoList(AT5SegmentInfo at5SegmentInfo) {
		at5SegmentInfoList.add(at5SegmentInfo);
	}
	
	public void addToNTESegmentInfoList(NTESegmentInfo nteSegmentInfo) {
		nteSegmentInfoList.add(nteSegmentInfo);
	}

	public void addToNameInfoList(NameInfo nameInfo) {
		nameInfoList.add(nameInfo);
	}

	public void addTolineItemInfoList(LineItemInfo lineItemInfo) {
		lineItemInfoList.add(lineItemInfo);
	}

	public void addTocommunicationInfoList(CommunicationInfo communicationInfo) {
		communicationInfoList.add(communicationInfo);
	}

	public void addToHazardousInfoList(HazardousInfo hazardousInfo) {
		hazardousInfoList.add(hazardousInfo);
	}

	public void addToOrderInfoList(OrderInfo orderInfo) {
		OrderInfoList.add(orderInfo);
	}

	public void addToL11SegmentInfoList(L11SegmentInfo l11SegmentInfo) {
		l11SegmentInfoList.add(l11SegmentInfo);
	}

}
