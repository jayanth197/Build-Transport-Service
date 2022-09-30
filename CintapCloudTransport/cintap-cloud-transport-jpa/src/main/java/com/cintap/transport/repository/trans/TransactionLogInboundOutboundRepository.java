/**
 * 
 */
package com.cintap.transport.repository.trans;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.cintap.transport.entity.trans.TransactionLogInboundOutbound;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author SurenderMogiloju
 *
 */
@Repository
public interface TransactionLogInboundOutboundRepository extends JpaRepository<TransactionLogInboundOutbound, Integer>{
	
	@Transactional
	@Modifying
	@Query(value ="UPDATE transaction_log_inbound_outbound SET is_sent = 1  WHERE id = :id", nativeQuery = true)
	void updateIsSent(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query(value ="UPDATE transaction_log_inbound_outbound SET is_sent = 1, sent_raw_file = :sentFile  WHERE bpi_log_id = :bpiLogId AND trans_type = :transType ", nativeQuery = true)
	void updateSentRawFile(@Param("sentFile") String sentFile, @Param("bpiLogId") Integer bpiLogId, @Param("transType") String transType);
	
	Optional<List<TransactionLogInboundOutbound>> findByBpiLogIdAndTransactionType(Integer bpiLogId, String transType);
}
