package NWeek_Mission.Week_Mission.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class MemberController {
    @GetMapping("/login")
    @ResponseBody
    public String login(){
        return "로그인";
    }
}
