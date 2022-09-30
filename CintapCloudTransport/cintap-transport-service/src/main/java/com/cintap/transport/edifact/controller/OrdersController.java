package com.cintap.transport.edifact.controller;

import java.util.Optional;

import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.PARSERTYPE;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.edifact.service.OrdersService;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;
import com.cintap.transport.repository.edifact.orders.OrdersHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
public class OrdersController {

	@Autowired
	OrdersService ordersService;

	@Autowired
	OrdersHeaderRepository ordersHeaderRepository;

	@PostMapping("/orders")
	public ResponseEntity<FileUploadParams> parseOrders(@RequestBody EdifactReqParam desadvReqParam) {
		log.info("Transport orders request : ");
		FileUploadParams fileUploadParams = null;

		fileUploadParams = FileUploadParams.builder().fileName("fileName")
				.partnerId(desadvReqParam.getSenderPartnerId()).senderPartnerId(desadvReqParam.getSenderPartnerId())
				.ediType("orders").ediVersion("4010").receiverPartnerId(desadvReqParam.getReceiverPartnerId())
				.receiverIsaId("zz").senderIsaId("zz").fileType("edi").actualFileType("edi")
				.parseType(PARSERTYPE.STANDARD.getValue()).source(TRANSACTIONSOURCE.IMPORT.getValue()).fileCount(1)
				.ediStandard("EDIFACT").trnType("96A").direction(DIRECTION.INBOUND.getDirection()).build();
		fileUploadParams =  ordersService.parseOrder(desadvReqParam, fileUploadParams);
		return new ResponseEntity<>(fileUploadParams, HttpStatus.OK);
	}

	@GetMapping("/orders/{bpiLogId}")
	public ResponseEntity<OrdersHeader> getOrdersWithBpiLogId(@PathVariable("bpiLogId") Integer bpiLogId) {
		log.info("Sending req to orders ");

		OrdersHeader ordersHeader = null;
		try {
			Optional<OrdersHeader> optOrdersHeader = ordersHeaderRepository.findByBpiLogId(bpiLogId);
			if (optOrdersHeader.isPresent()) {
				ordersHeader = optOrdersHeader.get();
			}
		} catch (Exception e) {

		}

		log.info("Sending req to orders completed successfully");
		return new ResponseEntity<>(ordersHeader, HttpStatus.OK);
	}
}
