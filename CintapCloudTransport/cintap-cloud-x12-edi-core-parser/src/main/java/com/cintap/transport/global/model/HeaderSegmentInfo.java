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
public class HeaderSegmentInfo {
	private STSegmentInfo stSegmentInfo;
	private B2SegmentInfo b2SegmentInfo;
	private B2ASegmentInfo b2aSegmentInfo;
	private List<L11SegmentInfo> l11SegmentInfoList = new ArrayList<>();
	private List<G62SegmentInfo> g62SegmentInfoList = new ArrayList<>();
	private MS3SegmentInfo ms3SegmentInfo;
	private List<AT5SegmentInfo> at5SegmentInfoList = new ArrayList<>();
	private PLDSegmentInfo pldSegmentInfo;
	private List<NTESegmentInfo> nteSegmentInfoList = new ArrayList<>();

	private List<NameInfo> nameInfoList = new ArrayList<>();
	private List<EquipementInfo> equipementInfoList = new ArrayList<>();

	public void addToL11SegmentInfoList(L11SegmentInfo l11SegmentInfo) {
		l11SegmentInfoList.add(l11SegmentInfo);
	}

	public void addToG62SegmentInfoList(G62SegmentInfo g62SegmentInfo) {
		g62SegmentInfoList.add(g62SegmentInfo);
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

	public void addToEquipementInfoList(EquipementInfo equipementInfo) {
		equipementInfoList.add(equipementInfo);
	}
}
