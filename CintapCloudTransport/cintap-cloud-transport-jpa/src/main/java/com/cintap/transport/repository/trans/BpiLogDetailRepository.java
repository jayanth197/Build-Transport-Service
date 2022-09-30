package com.cintap.transport.repository.trans;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.trans.BpiLogDetail;


@Repository
public interface BpiLogDetailRepository extends CrudRepository<BpiLogDetail, Integer>{
	Optional<List<BpiLogDetail>> findByLogHeaderId(Integer logHeaderId);	
}
