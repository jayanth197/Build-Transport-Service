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
public class OrderInfo {
	private OIDSegmentInfo oidSegmentInfo;
	private List<LADSegmentInfo> ladSegmentInfoList = new ArrayList<>();
	
	
	public void addToladSegmentInfoList(LADSegmentInfo ladSegmentInfo) {
		ladSegmentInfoList.add(ladSegmentInfo);
	}
	
}	
