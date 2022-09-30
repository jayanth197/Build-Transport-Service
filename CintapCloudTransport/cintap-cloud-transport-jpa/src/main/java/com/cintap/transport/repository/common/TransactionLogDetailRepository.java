/**
 * 
 */
package com.cintap.transport.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.TransactionLogDetail;

/**
 * @author SurenderMogiloju
 *
 */
@Repository
public interface TransactionLogDetailRepository extends JpaRepository<TransactionLogDetail, Integer>{
	
	
}
