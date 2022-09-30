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
public class MEASegmentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String measurementReferenceIDCode;
	private String measurementQualifier;
	private String measurementValue;
	private String compositeUnitOfMeasure;
	private String unitOrBasisForMeasurementCode;
	private String Exponent;
	private String multiplier;
	

}
