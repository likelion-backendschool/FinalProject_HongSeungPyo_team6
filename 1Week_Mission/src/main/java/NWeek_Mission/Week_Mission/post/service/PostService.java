package NWeek_Mission.Week_Mission.post.service;

import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public void write(Member member, String subject, String content, String contentHtml) {
        Post post = Post.builder()
                    .subject(subject)
                    .content(content)
                    .contentHtml(contentHtml)
                    .build();
        postRepository.save(post);
    }
}
