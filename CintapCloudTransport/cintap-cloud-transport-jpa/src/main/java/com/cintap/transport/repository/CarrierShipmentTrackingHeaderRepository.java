package com.cintap.transport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cintap.transport.entity.tracking214.CarrierShipmentTrackingHeader;

public interface CarrierShipmentTrackingHeaderRepository extends JpaRepository<CarrierShipmentTrackingHeader, Integer>{

	Optional<CarrierShipmentTrackingHeader> findByBpiTransId(Integer bpiLogId);
}
