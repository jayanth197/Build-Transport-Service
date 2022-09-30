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
public class HazardousInfo {
	private LH1SegmentInfo lh1SegmentInfo;
	private List<LH2SegmentInfo> lh2SegmentInfoList = new ArrayList<>();
	private List<LH3SegmentInfo> lh3SegmentInfoList = new ArrayList<>();
	private List<LFHSegmentInfo> lfhSegmentInfoList = new ArrayList<>();

	public void addToLH2SegmentInfoList(LH2SegmentInfo lh2SegmentInfo) {
		lh2SegmentInfoList.add(lh2SegmentInfo);
	}

	public void addTolh3SegmentInfoList(LH3SegmentInfo lh3SegmentInfo) {
		lh3SegmentInfoList.add(lh3SegmentInfo);
	}

	public void addTolfhSegmentInfoList(LFHSegmentInfo lfhSegmentInfo) {
		lfhSegmentInfoList.add(lfhSegmentInfo);
	}

}
