package NWeek_Mission.Week_Mission.post.repository;

import NWeek_Mission.Week_Mission.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> searchQsl( String kw);
}