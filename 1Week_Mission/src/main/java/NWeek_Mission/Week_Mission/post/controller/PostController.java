package NWeek_Mission.Week_Mission.post.controller;

import NWeek_Mission.Week_Mission.member.exception.SignupEmailDuplicatedException;
import NWeek_Mission.Week_Mission.member.exception.SignupUsernameDuplicatedException;
import NWeek_Mission.Week_Mission.post.dto.PostCrateForm;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/list")
    public String list(Model model){
        List<Post> postList = postService.findAllPost();
        model.addAttribute("postList",postList);
        return "/post/list";
    }
    @GetMapping("/write")
    public String showWrite(){
        return "/post/write";
    }
    @PostMapping("/write")
    public String write(@Valid PostCrateForm postCrateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/post/write";
        }

        try {
            postService.write(postCrateForm.getSubject(),
                    postCrateForm.getContent());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "/member/join";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "/member/join";
        }
        return "/post/write";
    }
}
