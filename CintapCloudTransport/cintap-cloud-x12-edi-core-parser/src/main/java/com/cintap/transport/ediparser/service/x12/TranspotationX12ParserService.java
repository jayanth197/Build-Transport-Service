package com.cintap.transport.ediparser.service.x12;

import com.cintap.transport.ediparser.dto.global.Response;

public interface TranspotationX12ParserService {
   public Response parse214(String ediInputString);
}
