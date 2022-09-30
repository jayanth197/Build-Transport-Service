/**
 * 
 */
package com.cintap.transport.model;

import java.util.List;

import com.cintap.transport.entity.trans.TransactionLog;

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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSearchResponse {
	Pageable pegable;
	List<TransactionLog> transactions;
	long totalRecords;
}
