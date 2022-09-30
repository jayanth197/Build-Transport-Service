package com.cintap.transport.repository.trans;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.model.TransactionAperakInfo;
import com.cintap.transport.model.TransactionSourceSummary;
import com.cintap.transport.model.TransactoinSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends PagingAndSortingRepository<TransactionLog, Integer>,QueryByExampleExecutor<TransactionLog>,
   JpaSpecificationExecutor<TransactionLog>
{
	
	@Query("SELECT p FROM TransactionLog p where (p.stpId=:tpId or p.rtpId=:tpId) and p.statusId=1 order by 1 desc")
	Optional<List<TransactionLog>> findByPartnerId(@Param("tpId") String tpId);
	
	@Query("SELECT p FROM TransactionLog p where (p.stpId=:tpId or p.rtpId=:tpId) order by 1 desc") // and stpSourceId in ('810','850','855','856') and p.statusId=1 - removed to display all trns
	Page<TransactionLog> findByPartnerIdWithPageOtcWithPagination(@Param("tpId") String tpId,Pageable page);
	
	@Query("SELECT p FROM TransactionLog p where (p.stpId=:tpId or p.rtpId=:tpId) order by 1 desc") // and stpSourceId in ('810','850','855','856') and p.statusId=1 - removed to display all trns
	List<TransactionLog> findByPartnerIdWithPageOtc(@Param("tpId") String tpId);
	
	@Query("SELECT p FROM TransactionLog p where (p.stpId=:tpId or p.rtpId=:tpId) and stpSourceId in ('204','210','214') and p.statusId=1 order by 1 desc")
	Page<TransactionLog> findByPartnerIdWithPageWithPagination(@Param("tpId") String tpId,Pageable page);
	
	@Query("SELECT p FROM TransactionLog p where (p.stpId=:tpId or p.rtpId=:tpId) and stpSourceId in ('204','210','214') and p.statusId=1 order by 1 desc")
	List<TransactionLog> findByPartnerIdWithPage(@Param("tpId") String tpId);
	
	Optional<TransactionLog> findByBpiLogId(@Param("bpiLogId") Integer bpiLogId);
	
	@Query(value="SELECT new com.cintap.transport.model.TransactoinSummary(t.stpSourceId as trnType, "
			+ " t.statusId as status, "
			+ " count(*) as trnCount)"
			+ " from TransactionLog t "
			+ " WHERE (t.stpId = :tpId OR t.rtpId = :tpId) AND "
			+ " DATE_FORMAT(t.createdDate,'%Y-%m') LIKE :month% "
			+ " GROUP BY t.stpSourceId,t.statusId "
			+ " ORDER BY t.stpSourceId ")
	Optional<List<TransactoinSummary>> dashboardSummary(@Param("tpId") String tpId, @Param("month") String month);

	@Query(value="SELECT new com.cintap.transport.model.TransactionSourceSummary(t.source as source, "
			+ " DATE_FORMAT(t.createdDate,'%Y-%m-%d') as createdDate,"
			+ " count(*) as trnCount)"
			+ " from TransactionLog t "
			+ " WHERE (t.stpId = :tpId OR t.rtpId = :tpId) AND "
			+ " DATE_FORMAT(t.createdDate,'%Y-%m') LIKE :month% "
			+ " GROUP BY t.source, DATE_FORMAT(t.createdDate,'%Y-%m-%d') ")
	Optional<List<TransactionSourceSummary>> dashboardSourceSummary(@Param("tpId") String tpId,@Param("month") String month);

	Optional<List<TransactionLog>> findByStpIdAndRtpIdAndTransactionTypeAndStatusId(String senderPartnerId, String receiverPartnerId, String transactionType, Integer statusId);
	Optional<List<TransactionLog>> findByStpIdAndRtpIdAndTransactionTypeAndFileTypeAndStatusId(String senderPartnerId, String receiverPartnerId, String transactionType, String fileType, Integer statusId);
	
	Optional<TransactionLog> findByStpIdAndRtpIdAndStpTransId(String senderPartnerId, String receiverPartnerId, String partnerTransactionId);

	Optional<TransactionLog> findByStpIdAndRtpIdAndGsControlId(String senderPartnerId, String receiverPartnerId, String gsControlId);
	
	@Query(value ="SELECT new com.cintap.transport.model.TransactionAperakInfo(t.bpiLogId as bpiLogId, t.stpTransId as partnerTransactionId,"
			+ " l.id as inOutId, l.sentRawFile as sentRawFile, l.ackType as ackType)"
			+ " FROM TransactionLog t "
			+ "JOIN TransactionLogInboundOutbound l on "
			+ "t.bpiLogId = l.bpiLogId "
			+ "WHERE t.stpId = :senderPartnerId AND t.rtpId = :receiverPartnerId "
			+ "AND l.transactionType = 'APERAK' AND l.isSent = 0")
	Optional<List<TransactionAperakInfo>> getTransactionLogForAperak(@Param("senderPartnerId") String senderPartnerId, @Param("receiverPartnerId") String receiverPartnerId);
	
	@Transactional
	@Modifying
	@Query(value ="UPDATE transaction_log SET ack_status = :ackStatus  WHERE bpi_log_id = :bpiLogId", nativeQuery = true)
	void updateAckStatus(@Param("bpiLogId") Integer bpiLogId, @Param("ackStatus") Integer ackStatus);
	
	@Transactional
	@Modifying
	@Query(value ="UPDATE transaction_log SET st_control_id = :stControlId  WHERE bpi_log_id = :bpiLogId", nativeQuery = true)
	void updateStControlId(@Param("bpiLogId") Integer bpiLogId, @Param("stControlId") String stControl);

}
