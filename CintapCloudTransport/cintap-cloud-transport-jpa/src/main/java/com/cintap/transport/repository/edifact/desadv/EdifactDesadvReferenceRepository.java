package com.cintap.transport.repository.edifact.desadv;



import com.cintap.transport.entity.edifact.desadv.EdifactDesadvReference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EdifactDesadvReferenceRepository extends JpaRepository<EdifactDesadvReference, Integer>{

}
