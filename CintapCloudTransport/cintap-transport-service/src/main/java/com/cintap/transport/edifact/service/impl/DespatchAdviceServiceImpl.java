package com.cintap.transport.edifact.service.impl;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.EditUtility;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.edifact.service.DespatchAdviceService;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;
import com.cintap.transport.inbound.StandardInboundDesAdvAsnEdiFactService;
import com.cintap.transport.orders.inbound.StandardInboundOrdersEdifactService;
import com.cintap.transport.repository.edifact.desadv.DespatchAdviceHeaderRepository;
import com.cintap.transport.repository.trans.TransactionLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DespatchAdviceServiceImpl implements DespatchAdviceService {

	@Autowired
	DespatchAdviceHeaderRepository edifactDesadvHeaderRepository;

	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	StandardInboundDesAdvAsnEdiFactService standardInboundDesAdvAsnEdiFactService;
	@Autowired
	StandardInboundOrdersEdifactService standardInboundOrdersEdifactService;

	@Override
	public FileUploadParams parseAsn(EdifactReqParam desadvReqParam, FileUploadParams fileUploadParams) {

		String ediFact = "UNA:+.?'\r\n" + "UNB+UNOB:1+ASUS:ZZ+BSAFL:ZZ+220818:2342+00000173'\r\n"
				+ "UNH+81846423+DESADV:D:97A:UN'\r\n" + "BGM+351+15220243082+9'\r\n" + "DTM+137:20220818:102'\r\n"
				+ "DTM+17:11110101:102'\r\n" + "DTM+132:11110101:102'\r\n" + "MOA+38:0:USD'\r\n" + "NAD+FW'\r\n"
				+ "NAD+SF+HKHKG++AIV'\r\n" + "TDT+20+++A:AIR'\r\n" + "CPS+1'\r\n" + "FTX+AAB+++CIP TO PORT'\r\n"
				+ "FTX+ALL+++OT:N'\r\n" + "PAC+0'\r\n" + "PCI+0'\r\n" + "RFF+ON:152122061002665'\r\n" + "PCI+0'\r\n"
				+ "RFF+MB:111-11111111'\r\n" + "PCI+0'\r\n" + "RFF+BH:HKGAE2208013'\r\n" + "PCI+0'\r\n" + "RFF+BM'\r\n"
				+ "PCI+0'\r\n" + "RFF+AF:000'\r\n" + "PCI+0'\r\n" + "RFF+CV'\r\n" + "LIN+1++90MB1BC0-M0EAY0:VP'\r\n"
				+ "PIA+1+:BP'\r\n" + "IMD+F++VP:::ROG CROSSHAIR X670E HERO'\r\n" + "IMD+F++BP'\r\n" + "QTY+12:48'\r\n"
				+ "QTY+21:1'\r\n" + "QTY+22:12'\r\n" + "QTY+35:999999'\r\n" + "CNT+2:1'\r\n" + "UNT+35+81846423'\r\n"
				+ "UNZ+1+00000173'\r\n" + "";
		String interchanges = EditUtility.convertEditToJosn(ediFact);
		fileUploadParams = standardInboundDesAdvAsnEdiFactService.processInboundRequest(interchanges, fileUploadParams);
		return fileUploadParams;
	}
}
