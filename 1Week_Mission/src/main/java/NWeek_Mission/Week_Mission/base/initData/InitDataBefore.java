package NWeek_Mission.Week_Mission.base.initData;

import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.service.MemberService;
import NWeek_Mission.Week_Mission.post.service.PostService;

public interface InitDataBefore {
    default void before(MemberService memberService, PostService postService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com");
        Member member2 = memberService.join("user2", "1234", "user2@test.com");
    }
}
