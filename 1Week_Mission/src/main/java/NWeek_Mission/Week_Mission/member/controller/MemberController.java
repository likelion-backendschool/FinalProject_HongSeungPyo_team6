package NWeek_Mission.Week_Mission.member.controller;

import NWeek_Mission.Week_Mission.member.dto.MemberContext;
import NWeek_Mission.Week_Mission.member.dto.MemberCreateForm;
import NWeek_Mission.Week_Mission.member.dto.MemberModifyForm;
import NWeek_Mission.Week_Mission.member.dto.MemberModifyPasswordForm;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.exception.SignupEmailDuplicatedException;
import NWeek_Mission.Week_Mission.member.exception.SignupUsernameDuplicatedException;
import NWeek_Mission.Week_Mission.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") // 보내는 사람 메일 주소
    private String from;

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

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setFrom(from); // 보낼 주소
        mimeMessageHelper.setTo(memberCreateForm.getEmail()); // 받을 주소
        mimeMessageHelper.setSubject("회원가입 축하 이메일"); // 제목

        StringBuilder body = new StringBuilder();
        body.append("eBook 회원가입을 축하합니다."); // 내용
        mimeMessageHelper.setText(body.toString(), true);
        javaMailSender.send(mimeMessage);

        return "redirect:/";
    }
    @GetMapping("/modify")
    public String showModify(MemberModifyForm memberModifyForm){
        return "/member/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid MemberModifyForm memberModifyForm, BindingResult bindingResult, @AuthenticationPrincipal MemberContext memberContext, HttpSession session){
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

        // 회원 수정 후 session 을 종료 시켜 다시 로그인 하게 만듦.
        session.invalidate();

        return "redirect:/";
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
}
