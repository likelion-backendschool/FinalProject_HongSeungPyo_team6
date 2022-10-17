package NWeek_Mission.Week_Mission.post.controller;

import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @RequestMapping("/list")
    public String list(Model model){
        List<Post> postList = postService.findAllPost();
        model.addAttribute("postList",postList);
        return "/post/list";
    }
}
