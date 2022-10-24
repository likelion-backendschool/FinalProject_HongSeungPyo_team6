package NWeek_Mission.Week_Mission;

import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final PostService postService;
    @RequestMapping("/")
    public String list(Model model){
        List<Post> postList = postService.findAllNewPost();
        model.addAttribute("postList",postList);
        return "/main";
    }
}
