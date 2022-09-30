package com.cintap.transport.edifact.service;


import com.cintap.transport.common.model.FileUploadParams;
import com.cintap.transport.edifact.model.EdifactReqParam;
import com.cintap.transport.entity.edifact.orders.OrdersHeader;

public interface OrdersService {
	FileUploadParams parseOrder(EdifactReqParam desadvReqParam, FileUploadParams fileUploadParams);
}
