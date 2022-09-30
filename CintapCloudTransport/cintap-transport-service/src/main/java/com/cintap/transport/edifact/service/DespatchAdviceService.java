package com.cintap.transport.edifact.service;


import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.entity.edifact.desadv.DespatchAdviceHeader;

public interface DespatchAdviceService {
	FileUploadParams parseAsn(EdifactReqParam desadvReqParam, FileUploadParams fileUploadParams);
}
