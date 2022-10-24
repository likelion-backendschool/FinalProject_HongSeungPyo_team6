package com.ll.exam.final__2022_10_08.app.order.service;

import com.ll.exam.final__2022_10_08.app.cartitem.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cartitem.service.CartItemService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import com.ll.exam.final__2022_10_08.app.order.repository.OrderRepository;
import com.ll.exam.final__2022_10_08.app.orderitem.entity.OrderItem;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;
    // 모든 장바구니로부터 주문 하기.
    @Transactional
    public Order createFromCart(Member buyer) {

        // 입력된 회원의 장바구니 아이템들을 전부 가져온다.
        List<CartItem> cartItems = cartItemService.getItemsByBuyer(buyer);

        List<OrderItem> orderItems = new ArrayList<>();

        // 모든 장바구니에 있는 것들 중 주문이 가능한 상품만 추가해주는 구문.
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            // 주문이 가능한 상품인지 체크.
            if (product.isOrderable()) {
                // 주문 가능한 상품이면 추가.
                orderItems.add(new OrderItem(product));
            }
            // 주문을 했으므로 장바구니에서는 제거.
            cartItemService.removeItem(cartItem);
        }

        return create(buyer, orderItems);
    }

    @Transactional
    public Order create(Member buyer, List<OrderItem> orderItems) {
        Order order = Order
                .builder()
                .buyer(buyer)
                .build();

        // 주문 상품 추가.
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }

}
