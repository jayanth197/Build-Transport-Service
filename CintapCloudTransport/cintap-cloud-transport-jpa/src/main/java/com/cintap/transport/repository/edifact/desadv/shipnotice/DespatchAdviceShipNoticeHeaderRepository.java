package com.cintap.transport.repository.edifact.desadv.shipnotice;



import java.util.Optional;

import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DespatchAdviceShipNoticeHeaderRepository extends JpaRepository<DespatchAdviceShipNoticeHeader, Integer>{
	Optional<DespatchAdviceShipNoticeHeader> findByBpiLogId(Integer bpiLogId);
}
