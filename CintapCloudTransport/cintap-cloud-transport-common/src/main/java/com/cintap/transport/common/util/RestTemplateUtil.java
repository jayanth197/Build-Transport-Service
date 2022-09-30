/**
 * 
 */
package com.cintap.transport.common.util;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SurenderMogiloju
 *
 */
@Service
@Slf4j
public class RestTemplateUtil {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<String> sendPostRequest(String url, MediaType produceType, MediaType acceptType,
			String requestStr) {
		log.info("url is : " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(produceType);
		headers.setAccept(Collections.singletonList(acceptType));
		HttpEntity<String> request = new HttpEntity<String>(requestStr, headers);

		final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return response;

	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
