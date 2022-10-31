package com.ll.exam.final__2022_10_08.app.withdraw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/withdraw")
public class WithdrawController {
    @GetMapping("/apply")
    public String showApply(){
        return "withdraw/apply";
    }
}
