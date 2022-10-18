package NWeek_Mission.Week_Mission.post.controller;

import NWeek_Mission.Week_Mission.member.dto.MemberContext;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.exception.SignupEmailDuplicatedException;
import NWeek_Mission.Week_Mission.member.exception.SignupUsernameDuplicatedException;
import NWeek_Mission.Week_Mission.member.service.MemberService;
import NWeek_Mission.Week_Mission.post.dto.PostCrateForm;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.exception.PostNotFoundException;
import NWeek_Mission.Week_Mission.post.service.PostService;
import NWeek_Mission.Week_Mission.posthashtag.entity.PostHashTag;
import NWeek_Mission.Week_Mission.posthashtag.entity.service.PostHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    private final PostHashTagService postHashTagService;
    @GetMapping("/list")
    public String showList(Model model){
        List<Post> postList = postService.findAllPost();
        model.addAttribute("postList",postList);
        return "/post/list";
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id){
        Post post = postService.findByIdPost(id).orElseThrow(() ->
                new PostNotFoundException("해당 글을 찾을수 없습니다.")
        );
        List<PostHashTag> postHashTagList = postHashTagService.getHashTags(post.getId());
        model.addAttribute("postHashTagList",postHashTagList);
        model.addAttribute("post",post);
        return "/post/detail";
    }
    @GetMapping("/write")
    public String showWrite(PostCrateForm postCrateForm){
        return "/post/write";
    }
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostCrateForm postCrateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/post/write";
        }

        Member member = memberService.findByUsername(memberContext.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을수 없습니다.")
        );

        try {
            postService.write(member,postCrateForm.getSubject(),
                    postCrateForm.getContent(),
                    postCrateForm.getPostKeywordContents());
        } catch (SignupEmailDuplicatedException e) {
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "/post/write";
        } catch (SignupUsernameDuplicatedException e) {
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "/post/write";
        }
        return "redirect:/post/list";
    }
}
