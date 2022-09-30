package com.cintap.transport.repository.trans;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends CrudRepository<TransactionType, Integer>{

}
