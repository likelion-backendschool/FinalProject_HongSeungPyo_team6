package NWeek_Mission.Week_Mission.member.service;


import NWeek_Mission.Week_Mission.member.dto.MemberContext;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.entity.MemberRole;
import NWeek_Mission.Week_Mission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 시큐리티가 특정 회원의 username을 받았을 때
    // 그 username에 해당하는 회원정보를 얻는 수단
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을수 없습니다.")
        );

        // 권한들을 담을 빈 리스트를 만든다.
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 권한 레벨이 7 이면 어드민 권한 획득.
        if (member.getAuthLevel() == 7) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        }
        // 닉네임이 존재 하면 작가회원, 존재하지 않을 경우 일반 회원.
        if (member.getNickname().trim().length() > 0){
            authorities.add(new SimpleGrantedAuthority(MemberRole.WRITER.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        }


        return new MemberContext(member);
    }
}