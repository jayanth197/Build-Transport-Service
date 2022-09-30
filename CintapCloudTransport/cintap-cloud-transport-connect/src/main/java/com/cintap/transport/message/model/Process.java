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
public class Process implements Serializable {

	private static final long serialVersionUID = 3529465747824254915L;

	private int id;

	private String name;

	private String description;

	private String type;

	private Integer partnerId;

	private String senderIsa;
	
	private Integer forPartner;

	private String receiverIsa;
	
	private String scriptType;

	private Integer mapId;

	private Integer connectionId;

	private Integer sleepTime;

	private String emailTo;
	
	private String reqQueueName;
	
	private String respQueueName;
	
	private Integer ack997Flag;

	private Integer isDelete;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;

	private Integer status;

	private String action;
}
