package NWeek_Mission.Week_Mission.post.service;

import NWeek_Mission.Week_Mission.member.dto.MemberContext;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.post.exception.PostNotFoundException;
import NWeek_Mission.Week_Mission.post.repository.PostRepository;
import NWeek_Mission.Week_Mission.posthashtag.service.PostHashTagService;
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

    public void write(Member member, String subject, String content,String contentHtml ,String keywordContentsStr) {
        Post post = Post.builder()
                    .subject(subject)
                    .content(content)
                    .contentHtml(contentHtml)
                    .author(member)
                    .build();

        postRepository.save(post);
        postHashTagService.write(member,post,keywordContentsStr);
    }

    public void modify(Member member, Post post, String subject, String content,String contentHtml, String keywordContentsStr) {
        post.setSubject(subject);
        post.setContent(content);
        post.setContentHtml(contentHtml);

        postRepository.save(post);
        postHashTagService.write(member,post,keywordContentsStr);
    }

    public List<Post> findAllNewPost() {
        return postRepository.findTop100ByOrderByCreateDateDesc();
    }

    public Optional<Post> findByIdPost(Long id) {
        return postRepository.findById(id);
    }


    
    public void deletePost(Long id) {
        Post post = findByIdPost(id).orElseThrow(() ->
                new PostNotFoundException("해당 글을 찾을수 없습니다.")
        );
        postRepository.delete(post);
    }

    public List<Post> search(String kw, MemberContext memberContext){
        List<Post> postList = postRepository.searchByKeywordAndMember(kw,memberContext);
        return postList;
    }

}
