package NWeek_Mission.Week_Mission.post.service;

import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.repository.PostRepository;
import NWeek_Mission.Week_Mission.posthashtag.entity.service.PostHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final PostHashTagService postHashTagService;
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public void write(Member member, String subject, String content,String keywordContentsStr) {
        Post post = Post.builder()
                    .subject(subject)
                    .content(content)
                    .contentHtml(content)
                    .author(member)
                    .build();

        postRepository.save(post);
        postHashTagService.write(member,post,keywordContentsStr);
    }

    public List<Post> findAllNewPost() {
        return postRepository.findTop100ByOrderByCreateDateDesc();
    }

    public Optional<Post> findByIdPost(Long id) {
        return postRepository.findById(id);
    }
}
