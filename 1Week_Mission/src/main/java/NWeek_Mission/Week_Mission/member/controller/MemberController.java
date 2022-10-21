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

        mailService.sendMail(memberCreateForm.getEmail(),"회원가입 축하 이메일","eBook 회원가입에 축하드립니다!");

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
                new UsernameNotFoundException("사용자를 찾을수 없습니다.")
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
                new UsernameNotFoundException("사용자를 찾을수 없습니다.")
        );

        if (!passwordEncoder.matches(MemberModifyPasswordForm.getOldPassword(),member.getPassword())) {
            bindingResult.rejectValue("oldPassword", "passwordIncorrect",
                    "현재 비밀번호가 일치하지 않습니다.");
            return "/member/modify_password";
        }
        if (!MemberModifyPasswordForm.getPassword().equals(MemberModifyPasswordForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "/member/modify_password";
        }
        memberService.modifyPassword(member, MemberModifyPasswordForm.getPassword());

        // 회원 수정 후 session 을 종료 시켜 다시 로그인 하게 만듦.
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
                    "입력하신 이메일로 아이디를 찾을 수 없습니다.");
            return "/member/find_username";
        }
        Member member = optMember.get();
        mailService.sendMail(memberFindUsernameForm.getEmail(),"eBook 아이디 찾기","eBook 해당 아이디는 " + member.getUsername() + " 입니다.");
        return "/member/find_username";
    }
}
