package com.ll.exam.final__2022_10_08.app.cartitem.service;

import com.ll.exam.final__2022_10_08.app.cartitem.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cartitem.repository.CartItemRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    public CartItem addItem(Member member, Product product) {
        CartItem cartItem = CartItem.builder()
                                    .member(member)
                                    .product(product)
                                    .build();
        cartItemRepository.save(cartItem);

        return cartItem;
    }


    public List<CartItem> getItemsByBuyer(Member buyer) {
        return cartItemRepository.findAllByMemberId(buyer.getId());
    }

    public void removeItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
