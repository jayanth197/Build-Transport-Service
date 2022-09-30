package com.cintap.transport.common.service;

//import com.cintap.ediparser.dto.global.Interchange;
import com.cintap.transport.common.model.FileUploadParams;

public interface StandardInboundService {
//	default FileUploadParams processInboundRequest(Interchange interchange, FileUploadParams fileUploadParams) throws Exception{
//		return fileUploadParams;
//	}

	default FileUploadParams processInboundRequest(String interchanges, FileUploadParams fileUploadParams) throws Exception{
		return fileUploadParams;
	}
	
	default FileUploadParams processInboundXMLRequest(String xmlData, FileUploadParams fileUploadParams) throws Exception{
		return fileUploadParams;
	}
}
