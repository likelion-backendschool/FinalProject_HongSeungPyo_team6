package NWeek_Mission.Week_Mission.base.initData;

import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.service.MemberService;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.service.PostService;

import java.util.ArrayList;
import java.util.List;

public interface InitDataBefore {
    default void before(MemberService memberService, PostService postService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com","user1");
        Member member2 = memberService.join("user2", "1234", "user2@test.com","user2");


        for (int i = 1; i<=200; i++){
             Post post = Post.builder()
                     .author(member1)
                     .content("내용"+i)
                     .subject("게시물"+i)
                     .contentHtml("Html"+i)
                     .build();
            postService.write(member1, post.getSubject(),post.getContent(),post.getContentHtml());
        }

    }
}
