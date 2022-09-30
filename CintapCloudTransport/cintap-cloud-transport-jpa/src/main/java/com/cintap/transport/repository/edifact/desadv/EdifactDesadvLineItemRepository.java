package com.cintap.transport.repository.edifact.desadv;

import com.cintap.transport.entity.edifact.desadv.EdifactDesadvLineItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EdifactDesadvLineItemRepository extends JpaRepository<EdifactDesadvLineItem, Integer>{

}
