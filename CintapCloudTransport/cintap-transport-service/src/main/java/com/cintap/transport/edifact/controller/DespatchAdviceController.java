package com.cintap.transport.edifact.controller;

import java.util.Optional;

import com.berryworks.edireader.json.fromedi.EdiToJson;
import com.cintap.transport.common.enums.DIRECTION;
import com.cintap.transport.common.enums.PARSERTYPE;
import com.cintap.transport.common.enums.TRANSACTIONSOURCE;
import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.edifact.service.DespatchAdviceService;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.model.ApiResponse;
import com.cintap.transport.repository.edifact.desadv.DespatchAdviceHeaderRepository;

import org.json.JSONArray;
import org.json.JSONObject;
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
public class DespatchAdviceController {

	@Autowired
	DespatchAdviceService desadvService;
	
	@Autowired
	DespatchAdviceHeaderRepository edifactDesadvHeaderRepository;

	@PostMapping("/desadvasn")
	public ResponseEntity<FileUploadParams> parseAsn(@RequestBody EdifactReqParam desadvReqParam) {
		log.info("Transport desadv request : ");
		ApiResponse apiResponse = null;
		FileUploadParams fileUploadParams = null;
		
		fileUploadParams = FileUploadParams.builder()
				.fileName("fileName")
				.partnerId(desadvReqParam.getSenderPartnerId())
				.senderPartnerId(desadvReqParam.getSenderPartnerId())
				.ediType("desadv")
				.ediVersion("4010")
				.receiverPartnerId(desadvReqParam.getReceiverPartnerId())
				.receiverIsaId("zz")
				.senderIsaId("zz")
				.fileType("edi")
				.actualFileType("edi")
				.parseType(PARSERTYPE.STANDARD.getValue())
				.source(TRANSACTIONSOURCE.IMPORT.getValue())
				.fileCount(1)
				.ediStandard("EDIFACT")
				.trnType("97A")
				.direction(DIRECTION.INBOUND.getDirection())
				.build();
		fileUploadParams = desadvService.parseAsn(desadvReqParam, fileUploadParams);
		return new ResponseEntity<>(fileUploadParams, HttpStatus.OK);
	}

	@GetMapping("/desadvasn/{bpiLogId}")
	public ResponseEntity<DespatchAdviceHeader> getDesadvAsnWithBpiLogId(@PathVariable("bpiLogId") Integer bpiLogId) {
		log.info("Sending req to Desadv ");

		DespatchAdviceHeader despatchAdviceHeader=null;
		try {
			Optional<DespatchAdviceHeader> optDespatchAdviceHeader = edifactDesadvHeaderRepository.findByBpiLogId(bpiLogId);
			if(optDespatchAdviceHeader.isPresent()) {
				despatchAdviceHeader = optDespatchAdviceHeader.get();
			}
		} catch (Exception e) {

		}

		log.info("Sending req to desadv completed successfully");
		String response = "Sending req to desadv completed successfully";
		return new ResponseEntity<>(despatchAdviceHeader, HttpStatus.OK);
	}
	
	@GetMapping("/desadvasn/test")
	public ResponseEntity<String> getTestJson(){
		String ediJson = "";
		String value = "";
		String ediData = "UNA:+.? 'UNB+UNOB:1+ASUS-TEST:ZZ+BSAFL:ZZ+220920:1315+00000029'UNH+00000001+APERAK:D:96A:UN'BGM+23:::IFTMAN+00000083+9+ER'DTM+137:20220920:102'DTM+334:20220920131241:204'FTX+AAI+++IFTMAN EDI Parser Error'RFF+ON'UNT+7+00000001'UNZ+1+00000029'";
		try {
		EdiToJson ediToJson = new EdiToJson();
		ediToJson.setFormatting(true);
		ediToJson.setAnnotated(true);
		ediToJson.setSummarize(false);
		
			ediJson = ediToJson.asJson(ediData);
			log.info("EdiJson is ::{}", ediJson);
			value = getEDIFACTDocumentType(ediJson);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return new ResponseEntity<>(ediJson, HttpStatus.OK);
	}
	
	private String getEDIFACTDocumentType(String ediJson) {
		JSONObject obj = new JSONObject(ediJson);
		String value = "";
		JSONArray interchangesArr = obj.getJSONArray("interchanges");
		JSONObject interchangesObj = interchangesArr.getJSONObject(0);
		JSONArray trnsactionsArr = interchangesObj.getJSONArray("transactions");
		JSONObject transactionObj = trnsactionsArr.getJSONObject(0);
		if(transactionObj.has("UNH_02_MessageIdentifier"))
		{
			JSONObject unh = (JSONObject) transactionObj.get("UNH_02_MessageIdentifier");
			if(unh.has("UNH_02_01_MessageType"))
				value = unh.getString("UNH_02_01_MessageType");
			
		}
//		return interchange.getGroup().getTransactionLst().get(0).getDocType();
		return value;
	}
}
