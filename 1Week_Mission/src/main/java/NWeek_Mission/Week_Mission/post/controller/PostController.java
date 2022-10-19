package NWeek_Mission.Week_Mission.post.controller;

import NWeek_Mission.Week_Mission.Util;
import NWeek_Mission.Week_Mission.member.dto.MemberContext;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.service.MemberService;
import NWeek_Mission.Week_Mission.post.dto.PostCrateForm;
import NWeek_Mission.Week_Mission.post.dto.PostModifyForm;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.exception.PostNotFoundException;
import NWeek_Mission.Week_Mission.post.service.PostService;
import NWeek_Mission.Week_Mission.posthashtag.entity.PostHashTag;
import NWeek_Mission.Week_Mission.posthashtag.service.PostHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String showList(Model model, @RequestParam(defaultValue = "") String kw, @AuthenticationPrincipal MemberContext memberContext){
        List<Post> postList = postService.search(kw,memberContext);
        model.addAttribute("postList",postList);
        return "/post/list";
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id){
        Post post = postService.findByIdPost(id).orElseThrow(() ->
                new PostNotFoundException("해당 글을 찾을수 없습니다.")
        );
        List<PostHashTag> postHashTagList = postHashTagService.getHashTags(post);
        model.addAttribute("postHashTagList",postHashTagList);
        model.addAttribute("post",post);
        return "/post/detail";
    }
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, PostModifyForm postModifyForm, @PathVariable Long id,Model model){
        Post post = postService.findByIdPost(id).orElseThrow(() ->
                new PostNotFoundException("해당 글을 찾을수 없습니다.")
        );
        List<PostHashTag> postHashTagList = postHashTagService.getHashTags(post);
        model.addAttribute("post",post);
        model.addAttribute("postHashTagList",postHashTagList);
        return "/post/modify";
    }
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, @Valid PostModifyForm postModifyForm, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()) {
            return "/post/modify";
        }
        Post post = postService.findByIdPost(id).orElseThrow(() ->
                new PostNotFoundException("해당 글을 찾을수 없습니다.")
        );
        Member member = memberService.findByUsername(memberContext.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을수 없습니다.")
        );
        String contentHtml = Util.markdown(postModifyForm.getContent());
       postService.modify(member,post,postModifyForm.getSubject(),postModifyForm.getContent(),contentHtml, postModifyForm.getPostKeywordContents());
        return "redirect:/post/list";
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
        String contentHtml = Util.markdown(postCrateForm.getContent());
        postService.write(member,postCrateForm.getSubject(),postCrateForm.getContent(), contentHtml,postCrateForm.getKeywords());
        return "redirect:/post/list";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        postService.deletePost(id);
        return "redirect:/post/list";
    }
}
