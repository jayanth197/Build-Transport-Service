/**
 * 
 */
package com.cintap.transport.global.model;

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
public class EndSegmentInfo {
	private L3SegmentInfo l3SegmentInfo;
	private SESegmentInfo segmentInfo;
	private GESegmentInfo geSegmentInfo;
	private IEASegmentInfo ieaSegmentInfo;
	
}
