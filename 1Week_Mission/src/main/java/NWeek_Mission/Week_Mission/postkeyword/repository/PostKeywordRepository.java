package NWeek_Mission.Week_Mission.postkeyword.repository;

import NWeek_Mission.Week_Mission.postkeyword.PostKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostKeywordRepository extends JpaRepository<PostKeyword,Long> {

    Optional<PostKeyword> findByContent(String keywordContent);
}
