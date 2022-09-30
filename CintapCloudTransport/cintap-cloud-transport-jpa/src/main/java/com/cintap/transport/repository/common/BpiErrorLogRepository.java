package com.cintap.transport.repository.common;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.ErrorLog;

@Repository
public interface BpiErrorLogRepository extends CrudRepository<ErrorLog, Integer>{

}
