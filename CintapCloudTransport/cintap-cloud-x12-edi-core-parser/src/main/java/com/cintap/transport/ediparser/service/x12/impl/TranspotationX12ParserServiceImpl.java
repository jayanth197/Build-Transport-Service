package com.cintap.transport.ediparser.service.x12.impl;

import java.util.ArrayList;
import java.util.List;

import com.cintap.transport.ediparser.dto.global.EdiResponse;
import com.cintap.transport.ediparser.dto.global.Response;
import com.cintap.transport.ediparser.dto.transaction.x12.TransportationCarrierShipmentStatusMessage214;
import com.cintap.transport.ediparser.mapping.x12.TranspotationMapper;
import com.cintap.transport.ediparser.parse.ParseEDI;
import com.cintap.transport.ediparser.service.x12.TranspotationX12ParserService;

public class TranspotationX12ParserServiceImpl implements TranspotationX12ParserService{
	@Override
	public Response parse214(String ediInputString) {
		List<TransportationCarrierShipmentStatusMessage214> lst214=new ArrayList<TransportationCarrierShipmentStatusMessage214>();
		Response res=new Response();
		TranspotationMapper transpotationMapper=new TranspotationMapper();		
		ParseEDI parseEDI=new ParseEDI();	
		EdiResponse ediResponse=parseEDI.convertToDto(ediInputString);		
		if(ediResponse.ediBase!=null) {
		lst214=transpotationMapper.Transpotation214Mapper(ediResponse);		
		res.setData(lst214);
		}
		
		res.setStatus(ediResponse.getStatus());
		res.setStatusMessage(ediResponse.getStatusMessage());
		res.setEdiAck(ediResponse.getEdiAck());
		
		System.out.println(res.getStatus());
		System.out.println(res.getStatusMessage());
		
		return res;
	}
}
