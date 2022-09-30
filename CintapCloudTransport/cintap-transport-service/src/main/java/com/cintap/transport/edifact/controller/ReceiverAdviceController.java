package com.cintap.transport.edifact.controller;

import java.util.Optional;

import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.PARSERTYPE;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.entity.edifact.iftman.ReceivingAdviceHeader;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;
import com.cintap.transport.repository.edifact.iftman.ReceivingAdviceHeaderRepository;

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
public class ReceiverAdviceController {

	@Autowired
	ReceivingAdviceHeaderRepository receivingAdviceHeaderRepository;
	
	@PostMapping("/receiverAdvice")
	public ResponseEntity<OrdersHeader> parseOrders(@RequestBody EdifactReqParam desadvReqParam) {
		log.info("Transport orders request : ");
		FileUploadParams fileUploadParams = null;

		fileUploadParams = FileUploadParams.builder().fileName("fileName")
				.partnerId(desadvReqParam.getSenderPartnerId()).senderPartnerId(desadvReqParam.getSenderPartnerId())
				.ediType("orders").ediVersion("4010").receiverPartnerId(desadvReqParam.getReceiverPartnerId())
				.receiverIsaId("zz").senderIsaId("zz").fileType("edi").actualFileType("edi")
				.parseType(PARSERTYPE.STANDARD.getValue()).source(TRANSACTIONSOURCE.IMPORT.getValue()).fileCount(1)
				.ediStandard("EDIFACT").trnType("96A").direction(DIRECTION.INBOUND.getDirection()).build();
		OrdersHeader ordersHeader = null;// desadvService.parseAsn(desadvReqParam, fileUploadParams);
		return new ResponseEntity<>(ordersHeader, HttpStatus.OK);
	}

	@GetMapping("/receiverAdvice/{bpiLogId}")
	public ResponseEntity<ReceivingAdviceHeader> getReceiverAdviceWithBpiLogId(@PathVariable("bpiLogId") Integer bpiLogId) {
		log.info("Sending req to orders ");

		ReceivingAdviceHeader receivingAdviceHeader = null;
		try {
			Optional<ReceivingAdviceHeader> optReceivingAdviceHeader = receivingAdviceHeaderRepository.findByBpiLogId(bpiLogId);
			if (optReceivingAdviceHeader.isPresent()) {
				receivingAdviceHeader = optReceivingAdviceHeader.get();
			}
		} catch (Exception e) {

		}

		log.info("Sending req to orders completed successfully");
		return new ResponseEntity<>(receivingAdviceHeader, HttpStatus.OK);
	}
}
