/**
 * 
 */
package com.cintap.transport.global.model;

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
public class StarterSegmentInfo {
	private ISASegmentInfo isaSegmentInfo;
	private GSSegmentInfo gsSegmentInfo;
	
	
}
