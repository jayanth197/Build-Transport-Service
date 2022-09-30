package com.cintap.transport.repository.edifact.orders;

import com.cintap.transport.entity.edifact.orders.OrdersAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersAddressRepository extends JpaRepository<OrdersAddress, Integer> {

}
