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
public class B2SegmentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tarriffServiceCode;
	private String standardCarrierAlphaCode;
	private String shipmentIdentificationNumber;
	private String weightUnitCode;
	private String shipmentMethodOfPayment;
	private String shipmentQualifier;
	private String totalEquipment;
	private String shipmentWeightCode;
	private String customsDocumentationHandlingCode;
	private String transportationTermsCode;
	private String paymentMethodCode;
}
