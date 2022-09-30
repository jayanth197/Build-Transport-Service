/**
 * 
 */
package com.cintap.transport.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.ProcessExecutionLog;

import java.lang.Integer;
import java.util.List;
import java.util.Optional;

/**
 * @author SurenderMogiloju
 *
 */
@Repository
public interface ProcessExecutionLogRepository extends JpaRepository<ProcessExecutionLog, Integer> {
	Optional<List<ProcessExecutionLog>> findByPartnerIdAndEdiTypeAndInboundOutboundIndAndIsOutboundGeneratedIsNullAndFileExecutionIdIsNotNullAndStatusCode(
			Integer partnerId, String ediType, String inboundOutboundInd, String sttausCode);
	

}
