package com.ll.exam.final__2022_10_08.app.order.controller;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cartitem.service.CartItemService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import com.ll.exam.final__2022_10_08.app.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartItemService cartItemService;
    private final Rq rq;
    @GetMapping("/list")
    public String showList(Model model){
        Member member = rq.getMember();
        Order order = orderService.getOrder(member);
        model.addAttribute("order",order);
        return "/order/list";
    }

    @PostMapping("/create")
    public String create(String ids){

        Member member = rq.getMember();
        orderService.createFromSelectCart(member,ids);
        return "redirect:/order/list";
    }

    @PostMapping("/makeOrder")
    public String makeOrder(){
        Member member = rq.getMember();
        orderService.createFromCart(member);
        return "redirect:/order/list";
    }
}
