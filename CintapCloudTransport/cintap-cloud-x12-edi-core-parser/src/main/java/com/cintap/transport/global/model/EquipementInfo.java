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
public class EquipementInfo {
	private N7SegmentInfo n7SegmentInfo;
	private MEASegmentInfo meaSegmentInfo;
	private List<M7SegmentInfo> m7SegmentInfoList = new ArrayList<>();
	
	public void addToM7SegmentInfo(M7SegmentInfo m7SegmentInfo) {
		m7SegmentInfoList.add(m7SegmentInfo);
	}
}
