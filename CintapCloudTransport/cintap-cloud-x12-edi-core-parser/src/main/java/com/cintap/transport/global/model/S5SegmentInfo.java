/**
 * 
 */
package com.cintap.transport.global.model;

import java.io.Serializable;

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
public class S5SegmentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stopSequenceNumber;
	private String stopReasonCode;
	private String weight;
	private String weightUnitCode;
	private String numberOfUnitsShipped;
	private String unitOrBasisForMeasurementCode;
	private String volume;
	private String volumeUnitQualifier;
	private String description01;
	private String description02;
	private String description03;
	
	
}
