/**
 * 
 */
package com.cintap.transport.common.message.model;

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
public class Connection implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer connectionId;
	
	private Integer partnerId;
	
	private String connectionName;
	
	private String direction;
	
	private String type;
	
	private String protocol;
	
	private String host;
	
	private Integer portNumber;
	
	private String userName;
	
	private String password;
	
	private Integer connectionTimeout;
	
	private String sourceDirectory;
	
	private String targetDirectory;
	
	private String filePath;
	
	private String fileName;
	
	private String archivePath;
	
	private String archiveFileName;
	
	private String errorPath;
	
	private Integer concurrentConnection;
	
	private String appendTimestamp;
	
	private String deleteAfterArchive;
	
	private Integer isDelete;
	
	private String createdDate;
	
	private String createdBy;
	
	private String updatedDate;
	
	private String updatedBy;
	
	private Integer status;
	
	private String action;
	
	private String ack997Path;
	
	private Integer healthCheck;
}
