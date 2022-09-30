package com.cintap.transport.repository.edifact.desadv;


import com.cintap.transport.entity.edifact.desadv.EdifactDesadvFreeTextDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EdifactDesadvFreeTextDetailsRepository extends JpaRepository<EdifactDesadvFreeTextDetails, Integer>{

}
