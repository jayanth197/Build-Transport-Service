package com.cintap.transport.repository.trans;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.AuditLog;

@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog, Integer>{
	 Optional<List<AuditLog>> findByPartnerId(@Param("tpId") Integer tpId);
	 Optional<List<AuditLog>> findByBpiLogId(@Param("tpId") Integer bpiLogId);
	 Optional<List<AuditLog>> findByComponentNameAndComponentRecId(@Param("cmpName") String cmpName,@Param("recId")Integer recId);
}
