package NWeek_Mission.Week_Mission.member.controller;


import NWeek_Mission.Week_Mission.mail.MailService;

import NWeek_Mission.Week_Mission.member.dto.*;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.exception.SignupEmailDuplicatedException;
import NWeek_Mission.Week_Mission.member.exception.SignupUsernameDuplicatedException;
import NWeek_Mission.Week_Mission.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;
    

    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }


    @GetMapping("/join")
    public String showJoin(MemberCreateForm memberCreateForm){
        return "/member/join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) throws MessagingException {
        if (bindingResult.hasErrors()) {
            return "/member/join";
        }

        try {
            memberService.join(memberCreateForm.getUsername(),
                    memberCreateForm.getPassword(), memberCreateForm.getEmail(),memberCreateForm.getNickname());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "/member/join";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "/member/join";
        }

        mailService.sendMail(memberCreateForm.getEmail(),"???????????? ?????? ?????????","eBook ??????????????? ??????????????????!");

        return "redirect:/";
    }
    @GetMapping("/modify")
    public String showModify(MemberModifyForm memberModifyForm){
        return "/member/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid MemberModifyForm memberModifyForm, BindingResult bindingResult, @AuthenticationPrincipal MemberContext memberContext){
        if (bindingResult.hasErrors()) {
            return "/member/modify";
        }
        Member member = memberService.findByUsername(memberContext.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("???????????? ????????? ????????????.")
        );
        try {
            memberService.modify(
                    member,
                    memberModifyForm.getNickname(),
                    memberModifyForm.getEmail()
            );
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "/member/modify";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "/member/modify";
        }

        memberContext.setModifyDate(member.getModifyDate());
        memberContext.setEmail(member.getEmail());
        memberContext.setNickname(member.getNickname());
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberContext, member.getPassword(), memberContext.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/modify";
    }
    @GetMapping("/modifyPassword")
    public String showModifyPassword(MemberModifyPasswordForm memberModifyForm){
        return "/member/modify_password";
    }
    @PostMapping("/modifyPassword")
    public String pwdModify(@Valid MemberModifyPasswordForm MemberModifyPasswordForm, BindingResult bindingResult, @AuthenticationPrincipal MemberContext memberContext, HttpSession session){
        if (bindingResult.hasErrors()) {
            return "/member/modify_password";
        }
        Member member = memberService.findByUsername(memberContext.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("???????????? ????????? ????????????.")
        );

        if (!passwordEncoder.matches(MemberModifyPasswordForm.getOldPassword(),member.getPassword())) {
            bindingResult.rejectValue("oldPassword", "passwordIncorrect",
                    "?????? ??????????????? ???????????? ????????????.");
            return "/member/modify_password";
        }
        if (!MemberModifyPasswordForm.getPassword().equals(MemberModifyPasswordForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordIncorrect",
                    "2?????? ??????????????? ???????????? ????????????.");
            return "/member/modify_password";
        }
        memberService.modifyPassword(member, MemberModifyPasswordForm.getPassword());

        // ?????? ?????? ??? session ??? ?????? ?????? ?????? ????????? ?????? ??????.
        session.invalidate();

        return "redirect:/";
    }
    @GetMapping("/findUsername")
    public String showFindUsername(MemberFindUsernameForm memberFindUsernameForm){
        return "/member/find_username";
    }
    @PostMapping("/findUsername")
    public String findUsername(@Valid MemberFindUsernameForm memberFindUsernameForm, BindingResult bindingResult) throws MessagingException {
        if (bindingResult.hasErrors()) {
            return "/member/find_username";
        }
        Optional<Member> optMember = memberService.findByEmail(memberFindUsernameForm.getEmail());
        if (!optMember.isPresent()){
            bindingResult.rejectValue("email", "emailNotFound",
                    "???????????? ???????????? ???????????? ?????? ??? ????????????.");
            return "/member/find_username";
        }
        Member member = optMember.get();
        mailService.sendMail(memberFindUsernameForm.getEmail(),"eBook ????????? ??????","eBook ?????? ???????????? " + member.getUsername() + " ?????????.");
        return "/member/find_username";
    }
}
