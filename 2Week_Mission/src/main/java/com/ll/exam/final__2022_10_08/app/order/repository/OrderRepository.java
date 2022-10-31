package com.ll.exam.final__2022_10_08.app.order.repository;

import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByBuyerId(Long BuyerId);
}
