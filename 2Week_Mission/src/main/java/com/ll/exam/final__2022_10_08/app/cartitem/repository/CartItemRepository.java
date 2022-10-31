package com.ll.exam.final__2022_10_08.app.cartitem.repository;


import com.ll.exam.final__2022_10_08.app.cartitem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByMemberId(Long memberId);



    Optional<CartItem> findByProductIdAndMemberId(Long productId, Long memberId);

    Optional<CartItem> findByIdAndMemberId(long cartId, long memberId);
}
