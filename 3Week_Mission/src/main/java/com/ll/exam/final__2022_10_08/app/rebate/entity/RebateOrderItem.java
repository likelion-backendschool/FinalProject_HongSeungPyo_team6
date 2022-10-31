package com.ll.exam.final__2022_10_08.app.rebate.entity;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;

import com.ll.exam.final__2022_10_08.app.cash.entity.CashLog;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;

import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class RebateOrderItem extends BaseEntity {
    @OneToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OrderItem orderItem;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CashLog rebateCashLog;

    // 가격
    private long price; // 권장판매가
    private long salePrice; // 실제판매가
    private long wholesalePrice; // 도매가
    private long pgFee; // 결제대행사 수수료
    private long payPrice; // 결제금액
    private long refundPrice; // 환불금액
    private boolean isPaid; // 결제여부
    private LocalDateTime payDate; // 결제날짜

    // 상품
    private String productSubject;

    private String sellerName;

    private double calculateRebatePrice;
    // 주문품목
    private LocalDateTime orderItemCreateDate;
    private boolean rebateAvailable;

    public RebateOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        order = orderItem.getOrder();
        product = orderItem.getProduct();
        price = orderItem.getPrice();
        salePrice = orderItem.getSalePrice();
        wholesalePrice = orderItem.getWholesalePrice();
        pgFee = orderItem.getPgFee();
        payPrice = orderItem.getPayPrice();
        refundPrice = orderItem.getRefundPrice();
        isPaid = orderItem.isPaid();
        payDate = orderItem.getPayDate();
        sellerName = orderItem.getProduct().getAuthor().getName();
        calculateRebatePrice = orderItem.getPayPrice() * 0.5;
        // 상품 추가데이터
        productSubject = orderItem.getProduct().getSubject();

        rebateAvailable = true;


        // 주문품목 추가데이터
        orderItemCreateDate = orderItem.getCreateDate();
    }

}
