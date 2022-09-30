/**
 * 
 */
package com.cintap.transport.message.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SurenderMogiloju
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BpiProcessBusinessRule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger id;

	private Integer processId;

	private String attributeId;

	private String operandId;

	private String ruleVale;

	private String conditionId;

	private String emailCondition;

	private String emailValue;

	private String createdBy;

	private Date createdDate;

	private String updatedBy;

	private Date updatedDate;

	private String status;
}
