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
public class CommunicationInfo {
	private G61SegmentInfo g61SegmentInfo;
	private List<L11SegmentInfo> l11SegmentInfoList = new ArrayList<>();
	
	public void addToL11SegmentInfoList(L11SegmentInfo l11SegmentInfo) {
		l11SegmentInfoList.add(l11SegmentInfo);
	}
	
}
