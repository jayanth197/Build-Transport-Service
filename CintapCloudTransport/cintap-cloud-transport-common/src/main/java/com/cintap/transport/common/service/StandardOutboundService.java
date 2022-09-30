package com.cintap.transport.common.service;

import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.entity.common.TransportServiceMapping;

public interface StandardOutboundService {
	String processOutboundRequest(Integer bpiLogId,FileUploadParams fileUploadParams,TransportServiceMapping transportServiceMapping) throws Exception;
}
