package com.cintap.transport.edifact.controller;

import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.edifact.service.impl.ConvertEdifactToXmlServiceImpl;
import com.cintap.transport.edifact.service.impl.ConvertXmlToEdifactServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/v1")
@Slf4j
public class EdifactConvertorController{
	
	@Autowired
	TransportCommonUtility cintapUtility;
	
	@Autowired
	ConvertEdifactToXmlServiceImpl convertEdifactToXmlServiceImpl;
	
	@Autowired
	ConvertXmlToEdifactServiceImpl convertXmlToEdifactServiceImpl;

	@GetMapping("/convert/{bpiLogId}/{type}")
	public ResponseEntity<String> convertorUtility(@PathVariable("bpiLogId") Integer bpiLogId,@PathVariable("type") String type)
	{
		String convertResult = null;
		try {
			log.info("ConvertorController | convertortUtility :  Request : bpiLogId - "+bpiLogId);
			String converstionType = type.toUpperCase();
			log.info(bpiLogId+" :: Request recieved for convert Transaction to XML for : "+type);
			if(StringUtils.isNoneEmpty(type)) {
				switch(converstionType) {
				case "XML":
					convertResult = convertEdifactToXmlServiceImpl.generateOutboundXML(bpiLogId);
					break;
				case "EDIFACT":
					convertResult = convertXmlToEdifactServiceImpl.generateOutboundEdifact(bpiLogId);
					break;
//				case "JSON":
//					convertResult=convertTransactionToJson.generateOutboundJson(bpiLogId);
//					break;	
//				case "FF":
//					convertResult=convertBpiTxnToFlatFileService.generateOutboundFlatFile(bpiLogId);
//					break;	
//				case "PDF":
//					//convertResult=convertTransactionToPdf.generateOutboundPdf(bpiLogId);
//					convertResult = fileGenerateService.generatePDFFile(poNumber, bpiLogId);
//					break;	
					
				default: break;	
				}
			}
			log.info("ConvertorController | convertortUtility : Response - "+TransportCommonUtility.convertObjectToJson(convertResult));
		} catch (Exception exception) {
			log.info("ConvertorController :: Got Exception while generation Outbound :: "+exception.getMessage());
			cintapUtility.saveErrorLog(exception);
		}
		return new ResponseEntity<>(convertResult,HttpStatus.OK);
	} 
}
