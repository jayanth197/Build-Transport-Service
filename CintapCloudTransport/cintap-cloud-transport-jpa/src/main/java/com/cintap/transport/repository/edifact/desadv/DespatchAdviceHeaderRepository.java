package com.cintap.transport.repository.edifact.desadv;



import java.util.Optional;

import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DespatchAdviceHeaderRepository extends JpaRepository<DespatchAdviceHeader, Integer>{
	Optional<DespatchAdviceHeader> findByBpiLogId(Integer bpiLogId);
}
