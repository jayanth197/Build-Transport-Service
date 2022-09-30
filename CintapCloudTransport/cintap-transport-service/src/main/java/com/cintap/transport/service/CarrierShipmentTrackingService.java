package com.cintap.transport.service;

import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeader;

public interface CarrierShipmentTrackingService {

	public Integer saveCarrierShipment(CarrierShipmentTrackingHeader carrierShipmentTrackingHeader, String txnSource,
			String processType);
}
