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
public class OIDSegmentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String referenceIdentification01;
	private String purchaseOrderNumber;
	private String referenceIdentification02;
	private String unitOrBasisForMeasurementCode;
	private String quantity;
	private String weightUnitCode;
	private String weight;
	private String volumeUnitQualifier;
	private String volume;
	
}
