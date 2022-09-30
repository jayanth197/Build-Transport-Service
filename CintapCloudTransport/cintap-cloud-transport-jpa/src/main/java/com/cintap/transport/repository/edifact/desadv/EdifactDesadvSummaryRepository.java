package com.cintap.transport.repository.edifact.desadv;



import com.cintap.transport.entity.edifact.desadv.EdifactDesadvSummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EdifactDesadvSummaryRepository extends JpaRepository<EdifactDesadvSummary, Integer>{

}
