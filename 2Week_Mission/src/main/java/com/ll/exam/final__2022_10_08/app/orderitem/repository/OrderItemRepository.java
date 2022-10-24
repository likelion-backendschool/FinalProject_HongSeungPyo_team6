package com.ll.exam.final__2022_10_08.app.orderitem.repository;

import com.ll.exam.final__2022_10_08.app.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findAllByOrderId(Long orderId);
}
