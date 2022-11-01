package com.ll.exam.final__2022_10_08.app.withdraw.controller;

import com.ll.exam.final__2022_10_08.app.base.exception.ActorCanNotRemoveException;
import com.ll.exam.final__2022_10_08.app.base.exception.InsufficientCashException;
import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.withdraw.form.WithdrawForm;
import com.ll.exam.final__2022_10_08.app.withdraw.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/withdraw")
@RequiredArgsConstructor
public class WithdrawController {
    private final Rq rq;
    private final WithdrawService withdrawService;
    private final MemberService memberService;
    @GetMapping("/apply")
    @PreAuthorize("isAuthenticated()")
    public String showApply(Model model){
        Member actor = rq.getMember();
        long actorRestCash = memberService.getRestCash(actor);
        model.addAttribute("actorRestCash",actorRestCash);
        return "withdraw/apply";
    }
    @PostMapping("/apply")
    @PreAuthorize("isAuthenticated()")
    public String apply(@Valid WithdrawForm withdrawForm){
        Member sessionMember = rq.getMember();
        Member member = memberService.findByUsername(sessionMember.getUsername()).get();
        withdrawService.apply(member,withdrawForm.getBankName(),withdrawForm.getBankAccountNo(),Long.parseLong(withdrawForm.getPrice()));
        return Rq.redirectWithMsg("/withdraw/apply", "출금 신청 되었습니다.");
    }
}
