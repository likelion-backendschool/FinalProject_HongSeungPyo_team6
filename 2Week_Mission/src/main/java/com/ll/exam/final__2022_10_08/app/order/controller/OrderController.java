package com.ll.exam.final__2022_10_08.app.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping
    @RequestMapping("/list")
    public String showList(){
        return "/order/list";
    }

//    @PostMapping
//    @RequestMapping("/")
//    public String
}
