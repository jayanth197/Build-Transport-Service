/**
 * 
 */
package com.cintap.transport.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.ProcessExecutionSequence;

/**
 * @author SurenderMogiloju
 *
 */
@Repository
public interface ProcessExecutionSequenceRepository extends JpaRepository<ProcessExecutionSequence, Integer>{
	
}
