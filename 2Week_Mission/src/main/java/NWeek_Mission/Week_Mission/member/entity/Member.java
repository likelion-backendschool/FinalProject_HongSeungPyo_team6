package NWeek_Mission.Week_Mission.member.entity;


import NWeek_Mission.Week_Mission.base.entity.BaseEntity;

import NWeek_Mission.Week_Mission.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Member extends BaseEntity {

    // 로그인 아이디
    @Column(unique = true)
    private String username;

    // 로그인 비밀번호
    private String password;

    // 필명
    @Column(unique = true)
    private String nickname;

    // 이메일
    @Column(unique = true)
    private String email;

    // 권한 레벨
    private Long authLevel;


    // 현재 회원이 가지고 있는 권한들을 List<GrantedAuthority> 형태로 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        return authorities;
    }
}