package NWeek_Mission.Week_Mission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String list(){

        return "/main";
    }
}
