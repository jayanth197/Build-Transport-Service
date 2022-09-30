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
public class LADSegmentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String packagingFormCode;
	private String ladingQuantity;
	private String weightUnitCode;
	private String unitWeight;
	private String productOrServiceIDQualifier01;
	private String productOrServiceID01;
	private String productOrServiceIDQualifier02;
	private String productOrServiceID02;

}
