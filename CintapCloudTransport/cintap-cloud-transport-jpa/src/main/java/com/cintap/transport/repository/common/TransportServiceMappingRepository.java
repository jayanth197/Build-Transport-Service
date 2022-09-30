package com.cintap.transport.repository.common;

import java.util.Optional;

import com.cintap.transport.entity.common.TransportServiceMapping;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportServiceMappingRepository extends JpaRepository<TransportServiceMapping, Integer>{

	Optional<TransportServiceMapping> findBySenderPartnerIdAndReceiverPartnerIdAndDirectionAndTransactionType(Integer senderPartnerId,Integer receiverPartnerId,String direction,String transType);
	
}
