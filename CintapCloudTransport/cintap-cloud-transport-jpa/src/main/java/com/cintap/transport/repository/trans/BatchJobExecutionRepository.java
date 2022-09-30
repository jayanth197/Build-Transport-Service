package com.cintap.transport.repository.trans;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.BatchJobExecution;

@Repository
public interface BatchJobExecutionRepository extends CrudRepository<BatchJobExecution, Integer>{
	Optional<List<BatchJobExecution>> findByPartnerId(String partnerId);
}
