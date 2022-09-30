package com.cintap.transport.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.GroupSequenceId;
@Repository
public interface GroupSeqIdRepository extends JpaRepository<GroupSequenceId, Integer>{

}
