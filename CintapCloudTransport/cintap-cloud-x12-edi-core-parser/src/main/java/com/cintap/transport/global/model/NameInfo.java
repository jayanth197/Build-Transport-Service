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
public class NameInfo {
	private N1SegmentInfo n1SegmentInfo;
	private N2SegmentInfo n2SegmentInfo;
	private List<N3SegmentInfo> n3SegmentInfoList = new ArrayList<>();
	private N4SegmentInfo n4SegmentInfo;
	private List<G61SegmentInfo> g61SegmentInfoList = new ArrayList<>();
	
	public void addTonNSegmentInfo(N3SegmentInfo n3SegmentInfo) {
		n3SegmentInfoList.add(n3SegmentInfo);
	}
	
	public void addToG61SegmentInfoList(G61SegmentInfo g61SegmentInfo) {
		g61SegmentInfoList.add(g61SegmentInfo);
	}
	
}
