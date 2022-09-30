package com.cintap.transport.repository.edifact.desadv;



import com.cintap.transport.entity.edifact.desadv.EdifactDesadvMessageDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EdifactDesadvMessageDateTimeRepository extends JpaRepository<EdifactDesadvMessageDateTime, Integer>{

}
