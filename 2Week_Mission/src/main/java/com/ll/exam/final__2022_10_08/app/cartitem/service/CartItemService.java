package com.ll.exam.final__2022_10_08.app.cartitem.service;

import com.ll.exam.final__2022_10_08.app.cartitem.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cartitem.repository.CartItemRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    public void addItem(Member member, Product product) {
        CartItem cartItem = CartItem.builder()
                                    .member(member)
                                    .product(product)
                                    .build();
        cartItemRepository.save(cartItem);
    }
}
