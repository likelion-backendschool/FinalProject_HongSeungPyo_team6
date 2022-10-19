package NWeek_Mission.Week_Mission.post.repository;

import NWeek_Mission.Week_Mission.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>,PostRepositoryCustom {

    List<Post> findTop100ByOrderByCreateDateDesc();
}
