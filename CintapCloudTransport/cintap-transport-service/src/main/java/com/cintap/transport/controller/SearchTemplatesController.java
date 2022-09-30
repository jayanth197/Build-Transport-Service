package com.cintap.transport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cintap.transport.entity.common.SearchTemplates;
import com.cintap.transport.repository.common.SearchTemplatesRepository;
import com.cintap.transport.service.SearchTemplatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1")
@Slf4j
public class SearchTemplatesController {

	@Autowired
	SearchTemplatesRepository  searchTemplatesRepository;

	@Autowired
	SearchTemplatesService searchTemplatesService;

	@GetMapping("/searchTemplates/{partner}/{view}")
	public ResponseEntity<List<SearchTemplates>> fetchSearchTemplates(
			@PathVariable("partner") String partner,@PathVariable("view") String view ) {
		List<SearchTemplates> searchTemplates = new ArrayList<>();
		Optional<List<SearchTemplates>> optSearchTemplates =
				searchTemplatesRepository.findByPartnerAndView(partner,view);
		if (optSearchTemplates.isPresent()) {
			searchTemplates = optSearchTemplates.get();
		}
		return new ResponseEntity<>(searchTemplates, HttpStatus.OK);
	}

	@PostMapping(value="/searchTemplates")
	public ResponseEntity<SearchTemplates> saveSearchTemplate(@RequestBody SearchTemplates searchTemplates){

		searchTemplates = searchTemplatesService.saveSearchTemplates(searchTemplates);
		return new ResponseEntity<>(searchTemplates,HttpStatus.OK);
	}

	@PutMapping(value="/searchTemplates/{id}")
	public ResponseEntity<SearchTemplates> updateSearchTemplate(@RequestBody SearchTemplates searchTemplates){

		searchTemplates = searchTemplatesService.updateSearchTemplates(searchTemplates);
		return new ResponseEntity<>(searchTemplates,HttpStatus.OK);
	}

}

