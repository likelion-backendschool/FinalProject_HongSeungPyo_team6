package NWeek_Mission.Week_Mission.member.repository;





import NWeek_Mission.Week_Mission.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);

    boolean existsByNickname(String nickname);

    Optional<Member> findByEmail(String email);
}