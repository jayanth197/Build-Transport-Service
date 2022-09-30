/**
 * 
 */
package com.cintap.transport.message.model;

import java.io.Serializable;

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
public class Maps implements Serializable{
	private static final long serialVersionUID = 2923850026875012194L;

	private int id;
	
	private String mapName;
	
	private String type;

	private String ediType;
	
	private String ediVersion;
	
	private String direction;
	
	private String fileType;
	
	private String description;

	private String transactionType;
	
	private String createdDate;
	
	private String createdBy;
	
	private String updatedDate;
	
	private String updatedBy;
	
	private Integer status;

	private String action;

}
