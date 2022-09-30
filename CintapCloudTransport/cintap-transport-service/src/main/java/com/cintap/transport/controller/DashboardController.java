package com.cintap.transport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.model.DashboardReqParam;
import com.cintap.transport.model.DashboardSummary;
import com.cintap.transport.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class DashboardController {

	@Autowired
	DashboardService dashboardService;

	@PostMapping("/dashboard")
	public ResponseEntity<DashboardSummary> getOtcDashBaordSummary(@RequestBody DashboardReqParam otcDashboardRequParam)
	{
		log.info("Transport dashboard request : "+TransportCommonUtility.convertObjectToJson(otcDashboardRequParam));
		DashboardSummary otcDashboardSummary = dashboardService.fetchDashboardSummary(otcDashboardRequParam);
		return new ResponseEntity<>(otcDashboardSummary,HttpStatus.OK);
	}
}
