package com.cintap.transport.edifact.service.impl;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.common.util.EditUtility;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.edifact.service.OrdersService;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;
import com.cintap.transport.orders.inbound.StandardInboundOrdersEdifactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	StandardInboundOrdersEdifactService standardInboundOrdersEdifactService;

	@Override
	public FileUploadParams parseOrder(EdifactReqParam desadvReqParam, FileUploadParams fileUploadParams) {

		OrdersHeader ordersHeader = null;
//		CintapParserService cintapParserService = new CintapParserServiceImpl();
//		DTOResponse response = cintapParserService.toDto(desadvReqParam.getEdiFactData());
//		System.out.println(response);
//		System.out.println(response.getStatusMessage());

		String ediData = "UNA:+.? 'UNB+UNOB:1+ASUS-TEST:ZZ+BSAFL:ZZ+220920:0844+00000013'UNH+52747540+ORDERS:D:96A:UN'BGM+220:9:ORDERS+21220058359'DTM+137:20220920:102'DTM+4:20220527:102'DTM+10:20220529:102'FTX+ALL+++T1:PR'FTX+ALL+++FORMA:N'FTX+ALL+++HEIGHT'FTX+ALL+++INSPECT'FTX+ALL+++CN:N'FTX+ALL+++OT:N'FTX+ALL+++SW:TRUCK'FTX+ALL+++TELEX'FTX+ALL+++PLT:N'FTX+ALL+++CML'FTX+SUR+++SEA-DHL-Maria del Carmen Mendez maria_del_carmen.mendez@dhl.com-786 98:86899'FTX+AAB+++EXW'RFF+IV:21220058359'RFF+OP:21220058359'RFF+UO:PO4505956937-SEA-BON'RFF+ACD:T-21220058359'RFF+VAT'NAD+ST+ACI-INGRAM MIRCO-CL-C-DHL-60+1801 NW 82ND AVENUE, DORAL, FL, 331:26, United States::33126+INGRAM MICRO CHILE SA'LOC+2+++:::US'NAD+BT+ACI-INGRAM MICRO-CL-C+AVDA. PROVIDENCIA 1760 OFICINA 1101:, PROVIDENCIA, SANTIAGO, SANTIAGO D:E CHILE, 9030882, Chile+INGRAM MICRO CHILE SA'LOC+2+++:::CL'NAD+FW+Will Call++WILL CALL'CTA+DL+Forwarder Contact Name' COM+Forwarder Telephone Number:TE' COM+Forwarder Fax Number:FX' CUX+2:USD'LIN+1++90MB0TV0-M0EAY0:VP'PIA+1'IMD+F++:::PRIME A320M-K//AM4,A320,SATA,M.2, MB'QTY+12:200'FTX+ALL+++STOCK:AZ'FTX+ALL+++CC'PRI+AAA:36'LIN+2++90MB17E0-M0EAY0:VP'PIA+1'IMD+F++:::PRIME H510M-E//LGA1200,H510,M.2,DP,HDMI,MB'QTY+12:220'FTX+ALL+++STOCK:AZ'FTX+ALL+++CC'PRI+AAA:55'LIN+3++90MB18A0-M0EAY0:VP'PIA+1'IMD+F++:::PRIME B460M-A R2.0//LGA1200,H470,USB3.2 GEN 1,MB'QTY+12:150'FTX+ALL+++STOCK:AZ'FTX+ALL+++CC'PRI+AAA:62'UNT+53+52747540'UNZ+1+00000013'";
		String interchanges = EditUtility.convertEditToJosn(ediData);
		fileUploadParams.setRawFile(ediData);
		fileUploadParams = standardInboundOrdersEdifactService.processInboundRequest(interchanges, fileUploadParams);
		return fileUploadParams;
	}

}
