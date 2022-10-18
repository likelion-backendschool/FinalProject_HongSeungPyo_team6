package NWeek_Mission.Week_Mission.base.initData;

import NWeek_Mission.Week_Mission.member.service.MemberService;
import NWeek_Mission.Week_Mission.post.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
