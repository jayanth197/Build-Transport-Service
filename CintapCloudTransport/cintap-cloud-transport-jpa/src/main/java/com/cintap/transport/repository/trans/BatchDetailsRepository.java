package com.cintap.transport.repository.trans;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchDetailsRepository extends CrudRepository<BatchJobLog, Integer>{
	
	Optional<List<BatchJobLog>> findByPartnerId(@Param("partnerId") Integer partnerId);
	
	@Modifying
	@Transactional
	@Query(value="Update bpi_batch_job set end_time=:endTime,batch_status=:batchStatus,no_of_transactions=:noOfTransactions,batch_log=:log where batch_id=:batchId",nativeQuery = true)
	void updateStatus(@Param("batchStatus") String batchStatus,@Param("endTime") String endTime,@Param("noOfTransactions") Integer noOfTransactions,@Param("log") String log,@Param("batchId") Integer batchId);

	Optional<BatchJobLog> findByBatchId(@Param("batchId") BigInteger batchId);
}