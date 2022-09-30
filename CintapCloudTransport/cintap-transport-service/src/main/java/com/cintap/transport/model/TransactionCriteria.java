/**
 * 
 */
package com.cintap.transport.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SurenderMogiloju
 *
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TransactionCriteria implements Serializable{
	private static final long serialVersionUID = 731226212792207414L;
	private String transactionNumber;
	private String transactionType;
	private Integer status;
	private Integer bpiLogId;
	private String senderPartner;
	private String receiverPartner;
	private String createdDate;
	private String fromDate;
	private String toDate;
	private String moduleType;
	private String fileType;
	private String processType;
	
	@NotNull(message = "filterType is mandatory")
	private String filterType;
	@NotNull(message = "Partner ID is mandatory with min size is 7")
	@Size(min = 7,message = "{txnsearch.partnerid.size}" )
	private String partnerId;
	
	/**
	 * Pagination properties
	 */
	private Integer pageNo; 
    private Integer pageSize;
    private String sortBy="bpiLogId"; //Dont remove this value, it will give exception during pagination
}

