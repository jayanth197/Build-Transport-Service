package com.cintap.transport.service;
import com.cintap.transport.entity.common.SearchTemplates;
import com.cintap.transport.repository.common.SearchTemplatesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchTemplatesService {

	@Autowired
	private SearchTemplatesRepository searchTemplatesRepository;
	
	public SearchTemplates saveSearchTemplates(SearchTemplates searchTemplates) {
		return searchTemplatesRepository.save(searchTemplates);
	}

	public SearchTemplates updateSearchTemplates(SearchTemplates searchTemplates) {
		return searchTemplatesRepository.save(searchTemplates);
	}
	
	
	
	
}

