package com.ll.exam.final__2022_10_08.app.security.service;

import com.ll.exam.final__2022_10_08.app.attr.service.AttrService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.repository.MemberRepository;
import com.ll.exam.final__2022_10_08.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AttrService attrService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).get();
        String authLevel = attrService.get("member__%d__extra__authLevel".formatted(member.getId()), "");
        return new MemberContext(member, member.genAuthorities(authLevel));
    }
}
