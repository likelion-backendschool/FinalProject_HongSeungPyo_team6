package NWeek_Mission.Week_Mission.posthashtag.entity.repository;

import NWeek_Mission.Week_Mission.posthashtag.entity.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHashTagRepository extends JpaRepository<PostHashTag,Long> {
    Optional<PostHashTag> findByPostIdAndPostKeywordId(Long postId, Long keywordId);


    List<PostHashTag> findByPostId(Long PostId);
}
