/**
 * 
 */
package com.cintap.transport.message.model;

import java.io.Serializable;
import java.util.List;

import com.cintap.transport.common.message.model.Connection;

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
public class ConnectionMapDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private Maps maps;
	private Process processObj;
	private Integer schedulerBatchJobExecutionId;
	private Integer senderPartnerId;
	private Integer receiverPartnerId;
	private Integer processFlowId;
	private String createdBy;
	private List<BpiProcessBusinessRule> ruleList;
}
