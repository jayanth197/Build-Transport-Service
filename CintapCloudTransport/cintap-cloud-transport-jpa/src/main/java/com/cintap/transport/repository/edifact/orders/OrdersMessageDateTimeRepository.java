package com.cintap.transport.repository.edifact.orders;



import com.cintap.transport.entity.edifact.orders.OrdersMessageDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersMessageDateTimeRepository extends JpaRepository<OrdersMessageDateTime, Integer>{

}
