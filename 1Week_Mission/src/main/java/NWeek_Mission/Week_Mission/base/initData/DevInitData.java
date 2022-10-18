package NWeek_Mission.Week_Mission.base.initData;

import NWeek_Mission.Week_Mission.member.service.MemberService;
import NWeek_Mission.Week_Mission.post.service.PostService;
import NWeek_Mission.Week_Mission.postkeyword.PostKeyword;
import NWeek_Mission.Week_Mission.postkeyword.service.PostKeywordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevInitData implements InitDataBefore{
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            PostService postService) {
        return args -> {
            before(memberService, postService);
        };
    }
}
