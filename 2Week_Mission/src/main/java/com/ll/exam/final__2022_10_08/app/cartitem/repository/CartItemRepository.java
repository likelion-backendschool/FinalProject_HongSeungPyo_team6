package com.ll.exam.final__2022_10_08.app.cartitem.repository;


import com.ll.exam.final__2022_10_08.app.cartitem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
