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
public class EDI204GlobalMessage {
	private StarterSegmentInfo starterSegentInfo = new StarterSegmentInfo();
	private HeaderSegmentInfo headerSegmentInfo = new HeaderSegmentInfo();
	private DetailInfo detailInfo = new DetailInfo();
	private EndSegmentInfo endSegmentInfo = new EndSegmentInfo();
}
