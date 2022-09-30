package com.cintap.transport.repository.edifact.iftman;



import java.util.Optional;

import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceHeader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceivingAdviceHeaderRepository extends JpaRepository<ReceivingAdviceHeader, Integer>{
	Optional<ReceivingAdviceHeader> findByBpiLogId(Integer bpiLogId);
}
