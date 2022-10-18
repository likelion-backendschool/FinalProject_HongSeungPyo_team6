package NWeek_Mission.Week_Mission.member.controller;

import NWeek_Mission.Week_Mission.member.dto.MemberCreateForm;
import NWeek_Mission.Week_Mission.member.exception.SignupEmailDuplicatedException;
import NWeek_Mission.Week_Mission.member.exception.SignupUsernameDuplicatedException;
import NWeek_Mission.Week_Mission.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }


    @GetMapping("/join")
    public String showJoin(MemberCreateForm memberCreateForm){
        return "/member/join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/member/join";
        }

        if (!memberCreateForm.getPassword().equals(memberCreateForm.getPasswordCheck())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
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

        return "redirect:/";
    }
}
