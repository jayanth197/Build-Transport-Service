package com.cintap.transport.repository.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cintap.transport.entity.common.SearchTemplates;


@Repository
public interface SearchTemplatesRepository extends  CrudRepository<SearchTemplates, Integer>{
	
	Optional<List<SearchTemplates>> findByPartnerAndView(String Partner,String View);
	

}

