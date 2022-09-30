/**
 * 
 */
package com.cintap.transport.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.FileExecutionSequence;

/**
 * @author SurenderMogiloju
 *
 */
@Repository
public interface FileExecutionSequenceRepository extends JpaRepository<FileExecutionSequence, Integer> {

}
