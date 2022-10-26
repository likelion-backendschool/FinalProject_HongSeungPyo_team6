package com.ll.exam.final__2022_10_08.app.cartitem.service;

import com.ll.exam.final__2022_10_08.app.cartitem.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cartitem.repository.CartItemRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    public CartItem addItem(Member member, Product product) {
        Optional<CartItem> oldCartItem = cartItemRepository.findByProductIdAndMemberId(product.getId(),member.getId());
        // 이미 존재하는 장바구니 아이템이면 추가 하지 않기.
        if (oldCartItem.isPresent()){
            return oldCartItem.get();
        }
        
        CartItem  cartItem = CartItem.builder()
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

    public Optional<CartItem> findByCartItemAndMember(long cartItemId, long memberId) {
        return cartItemRepository.findByIdAndMemberId(cartItemId,memberId);
    }


}
