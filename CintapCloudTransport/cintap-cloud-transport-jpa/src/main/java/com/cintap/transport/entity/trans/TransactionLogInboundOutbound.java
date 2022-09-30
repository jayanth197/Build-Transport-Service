/**
 * 
 */
package com.cintap.transport.entity.trans;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SurenderMogiloju
 *
 */
@Table(name="transaction_log_inbound_outbound")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLogInboundOutbound implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="bpi_log_id")
	private Integer bpiLogId;
	
	@Column(name="file_type")
	private String fileType;

	@Column(name="file_name")
	private String fileName;
	
	@Column(name="trans_type")
	private String transactionType;

	@Column(name="ack_type")
	private String ackType;

	@Column(name="ack_ref_number")
	private String ackRefNumber;
	
	@Column(name="received_raw_file")
	private String receivedRawFile;

	@Column(name="sent_raw_file")
	private String sentRawFile;

	@Column(name="is_sent")
	private boolean isSent;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="updated_date")
	private Timestamp updatedDate;
}
