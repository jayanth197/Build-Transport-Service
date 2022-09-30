package com.cintap.transport.repository.trans;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.trans.BpiLogHeader;


@Repository
public interface BpiLogHeaderRepository extends CrudRepository<BpiLogHeader, Integer>{
	
	Optional<List<BpiLogHeader>> findBySenderPartnerIdOrderByLogHdrIdDesc(Integer senderPartnerId);
	
	Optional<List<BpiLogHeader>> findByBpiLogId(Integer bpiLogId);
	
	Optional<BpiLogHeader> findByBpiLogIdAndDirection(Integer bpiLogId,String direction);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE bpi_log_hdr SET bpi_logid=:bpiLogId WHERE log_hdr_id=:headerId",nativeQuery = true)
	void updateBpiLogId(int bpiLogId,Integer headerId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE bpi_log_hdr SET batch_status=:status WHERE log_hdr_id=:headerId",nativeQuery = true)
	void updateStatus(String status,Integer headerId);
}
