package com.cintap.transport.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeader;
import com.cintap.transport.model.ApiResponse;
import com.cintap.transport.repository.CarrierShipmentTrackingHeaderRepository;
import com.cintap.transport.service.CarrierShipmentTrackingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1")
@Slf4j
public class CarrierShipmentTrackingController {

	@Autowired
	CarrierShipmentTrackingHeaderRepository carrierShipmentTrackingHeaderRepository;
	@Autowired
	CarrierShipmentTrackingService carrierShipmentTrackingService;

	@GetMapping("/shipmenttracking/{bpiLogId}")
	public ResponseEntity<CarrierShipmentTrackingHeader> fetchShipmentTracking(
			@PathVariable("bpiLogId") Integer bpiLogId) {
		CarrierShipmentTrackingHeader carrierShipmentTrackingHeader = null;
		Optional<CarrierShipmentTrackingHeader> optCarrierShipmentTrackingHeader = carrierShipmentTrackingHeaderRepository
				.findByBpiTransId(bpiLogId);
		if (optCarrierShipmentTrackingHeader.isPresent()) {
			carrierShipmentTrackingHeader = optCarrierShipmentTrackingHeader.get();
		}
		return new ResponseEntity<>(carrierShipmentTrackingHeader, HttpStatus.OK);
	}

	@PostMapping("/shipmenttracking")
	public ResponseEntity<ApiResponse> shipmentTracking(
			@RequestBody CarrierShipmentTrackingHeader carrierShipmentTrackingHeader) {

		log.info("CarrierShipmentTrackingController | shipmentTracking - Request : "
				+ TransportCommonUtility.convertObjectToJson(carrierShipmentTrackingHeader));
		Integer transactionId = null;
		ApiResponse apiResponse = null;
		transactionId = carrierShipmentTrackingService.saveCarrierShipment(carrierShipmentTrackingHeader,
				TRANSACTIONSOURCE.MANUAL.getValue(), TRANSACTIONSOURCE.MANUAL.getValue());
		apiResponse = ApiResponse.builder().bpiTransId(transactionId).statusCode("0000")
				.statusMessage("Shipment created successfully").build();
		log.info("CarrierShipmentTrackingController | shipmentTracking : Response - "
				+ TransportCommonUtility.convertObjectToJson(apiResponse));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}
