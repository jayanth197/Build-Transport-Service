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
public class N1SegmentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String entityIdentifierCode;
	private String name;
	private String identificationCodeQualifier;
	private String identificationCode;
	private String entityRelationshipCode;
	private String entityIdentifierCode06;
	
}
