package com.cintap.transport.repository.edifact.orders;



import java.util.Optional;

import com.cintap.transport.entity.edifact.orders.OrdersHeader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersHeaderRepository extends JpaRepository<OrdersHeader, Integer>{
	Optional<OrdersHeader> findByBpiLogId(Integer bpiLogId);
}
