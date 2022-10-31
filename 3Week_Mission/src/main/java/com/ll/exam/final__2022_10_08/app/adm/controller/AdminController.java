package com.ll.exam.final__2022_10_08.app.adm.controller;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.rebate.entity.RebateOrderItem;
import com.ll.exam.final__2022_10_08.app.rebate.service.RebateOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final RebateOrderItemService rebateOrderItemService;
    private final Rq rq;
    @GetMapping("/rebate/makeData")
    public String showRebateMakeData(){
        return "adm/rebate/makeData";
    }

    @PostMapping("/rebate/makeData")
    public String rebateMakeDate(String yearMonth){
        rebateOrderItemService.makeData(yearMonth);
        return rq.redirectWithMsg("/adm/rebate/rebateOrderItemList","정산 데이터가 생성 되었습니다.");
    }

    @GetMapping("/rebate/rebateOrderItemList")
    public String showRebateOrderItemList(Model model){
        List<RebateOrderItem> rebateOrderItems = rebateOrderItemService.getReBateOrderItems();
        model.addAttribute("items", rebateOrderItems);
        return "adm/rebate/orderItemList";
    }
    @PostMapping("/rebate")
    public String rebate(String ids){
        rebateOrderItemService.rebateOrderItem(ids);
        return rq.redirectWithMsg("/adm/rebate/rebateOrderItemList","정산이 완료 되었습니다.");
    }
}
