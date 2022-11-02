package com.ll.exam.final__2022_10_08.app.rebate.service;

import com.ll.exam.final__2022_10_08.app.cash.entity.CashLog;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.order.entity.OrderItem;
import com.ll.exam.final__2022_10_08.app.order.service.OrderService;

import com.ll.exam.final__2022_10_08.app.rebate.entity.RebateOrderItem;
import com.ll.exam.final__2022_10_08.app.rebate.repository.RebateOrderItemRepository;
import com.ll.exam.final__2022_10_08.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebateOrderItemService {
    private final OrderService orderService;
    private final RebateOrderItemRepository rebateOrderItemRepository;

    private final MemberService memberService;

    public List<RebateOrderItem> getReBateOrderItems() {
        return rebateOrderItemRepository.findAll();
    }

    public void makeData(String yearMonth){
        int monthEndDay = Ut.date.getEndDayOf(yearMonth);

        String fromDateStr = yearMonth + "-01 00:00:00.000000";
        String toDateStr = yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay);
        LocalDateTime fromDate = Ut.date.parse(fromDateStr);
        LocalDateTime toDate = Ut.date.parse(toDateStr);
        List<OrderItem> orderItemList = orderService.getOrders(fromDate,toDate);

        List<RebateOrderItem> rebateOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItemList){
            RebateOrderItem oldRebateOrderItem = rebateOrderItemRepository.findByOrderItemId(orderItem.getId());
            if (oldRebateOrderItem != null) {
                rebateOrderItemRepository.delete(oldRebateOrderItem);
            }
            rebateOrderItems.add(new RebateOrderItem(orderItem));
        }
        rebateOrderItemRepository.saveAll(rebateOrderItems);
    }
    public void rebateOrderItems(){
        List<RebateOrderItem> rebateOrderItems = rebateOrderItemRepository.findAllByRebateAvailable(true);
        for (RebateOrderItem rebateOrderItem : rebateOrderItems){
            CashLog cashLog = memberService.addCash(
                    rebateOrderItem.getProduct().getAuthor(),
                    rebateOrderItem.getCalculateRebatePrice(),
                    "정산__%d__지급__예치금".formatted(rebateOrderItem.getOrderItem().getId())
            ).getData().getCashLog();
            rebateOrderItem.setRebateAvailable(false);
            rebateOrderItem.setRebateCashLog(cashLog);
        }
        rebateOrderItemRepository.saveAll(rebateOrderItems);
        return;
    }
    public void rebateOrderItem(String ids) {
        if (ids == null || ids.trim().length() == 0 ){
            rebateOrderItems();
            return;
        }
        String idBits[] = ids.split(",");
        List<RebateOrderItem> rebateOrderItems = new ArrayList<>();
        for (String id : idBits){
            RebateOrderItem rebateOrderItem = rebateOrderItemRepository.findById(Long.parseLong(id)).get();
            CashLog cashLog = memberService.addCash(rebateOrderItem.getProduct().getAuthor(), rebateOrderItem.getCalculateRebatePrice(), "정산__%d__지급__예치금".formatted(rebateOrderItem.getOrderItem().getId())
            ).getData().getCashLog();
            rebateOrderItem.setRebateAvailable(false);
            rebateOrderItem.setRebateCashLog(cashLog);
            rebateOrderItems.add(rebateOrderItem);
        }
        rebateOrderItemRepository.saveAll(rebateOrderItems);
    }
}
