package NWeek_Mission.Week_Mission.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostController {
    @RequestMapping("/")
    public String main(){
        return "/post/list";
    }
}
