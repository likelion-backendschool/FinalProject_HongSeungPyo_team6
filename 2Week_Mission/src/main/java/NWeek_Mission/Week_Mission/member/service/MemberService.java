package NWeek_Mission.Week_Mission.member.service;



import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.member.entity.MemberRole;
import NWeek_Mission.Week_Mission.member.exception.SignupEmailDuplicatedException;
import NWeek_Mission.Week_Mission.member.exception.SignupUsernameDuplicatedException;
import NWeek_Mission.Week_Mission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member join(String username, String password, String email, String nickname) {
        Member member = null;
        if (nickname.trim().length() > 0){
            member = Member.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .nickname(nickname)
                    .authLevel(4L)
                    .build();
        } else {
             member = Member.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .nickname(nickname)
                     .authLevel(3L)
                    .build();
        }

        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            if (memberRepository.existsByUsername(username)) {
                throw new SignupUsernameDuplicatedException("이미 사용중인 username 입니다.");
            } else {
                throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
            }
        }


        return member;
    }
    public Member modify(Member member, String nickname, String email) {

        member.setNickname(nickname);
        member.setAuthLevel(4L);
        member.setEmail(email);

        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            if (memberRepository.existsByNickname(nickname)) {
                throw new SignupUsernameDuplicatedException("이미 사용중인 nickname 입니다.");
            } else {
                throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
            }
        }


        return member;
    }

    public void modifyPassword(Member member, String password) {
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
