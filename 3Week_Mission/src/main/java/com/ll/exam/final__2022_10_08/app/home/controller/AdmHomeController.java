package com.ll.exam.final__2022_10_08.app.home.controller;

import com.ll.exam.final__2022_10_08.app.attr.service.AttrService;
import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdmHomeController {
    private final MemberService memberService;
    private final AttrService attrService;
    private final Rq rq;
    @GetMapping("")
    public String showIndex() {
        return "redirect:/adm/home/main";
    }

    @GetMapping("/home/main")
    public String showMain(Model model) {
        List<Member> members = memberService.getMembers();
        model.addAttribute("members", members);
        return "adm/home/main";
    }
    @PostMapping("/authority/{memberId}")
    public String adminAuthority(@PathVariable long memberId) {
        String authLevel = attrService.get("member__%d__extra__authLevel".formatted(memberId), "");

        if (authLevel.trim().length() == 0 || authLevel == null ){
            attrService.set("member__%d__extra__authLevel".formatted(memberId), "7");
            return rq.redirectWithMsg("/adm/home/main", "유저 %d번에게 관리자 권한을 주었습니다.".formatted(memberId));
        }

        return rq.redirectWithMsg("/adm/home/main", "유저 %d번은 관리자 권한이 이미 있습니다.".formatted(memberId));
    }
}

