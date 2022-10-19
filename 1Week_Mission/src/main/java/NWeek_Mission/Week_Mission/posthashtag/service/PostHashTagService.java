package NWeek_Mission.Week_Mission.posthashtag.service;

import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.posthashtag.entity.PostHashTag;
import NWeek_Mission.Week_Mission.posthashtag.repository.PostHashTagRepository;
import NWeek_Mission.Week_Mission.postkeyword.entity.PostKeyword;
import NWeek_Mission.Week_Mission.postkeyword.service.PostKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostHashTagService {
    private final PostHashTagRepository postHashTagRepository;
    private final PostKeywordService postKeywordService;
    public void write(Member member,Post post, String keywordContentsStr) {
        List<PostHashTag> oldHashTags = getHashTags(post);



        List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        List<PostHashTag> needToDelete = new ArrayList<>();

        for(PostHashTag oldHashTag : oldHashTags ){
            oldHashTag.getPostKeyword().getContent();

            boolean contains = keywordContents.stream().anyMatch(s -> s.equals(oldHashTag.getPostKeyword().getContent()));

            if (contains == false){
                needToDelete.add(oldHashTag);
            }
        }

        keywordContents.forEach(keywordContent -> {
            saveHashTag(member,post,keywordContent);
        });
        needToDelete.forEach(postHashTag -> {
            postHashTagRepository.delete(postHashTag);
        });
    }

    private PostHashTag saveHashTag(Member member,Post post, String keywordContent) {
        PostKeyword postKeyword = postKeywordService.save(keywordContent);

        Optional<PostHashTag> optPostHashTag = postHashTagRepository.findByPostIdAndPostKeywordId(post.getId(), postKeyword.getId());

        if(optPostHashTag.isPresent()){
            return optPostHashTag.get();
        }
        PostHashTag postHashTag = PostHashTag.builder()
                                            .member(member)
                                            .post(post)
                                            .postKeyword(postKeyword)
                                            .build();
        postHashTagRepository.save(postHashTag);
        return postHashTag;
    }

    public List<PostHashTag> getHashTags(Post post) {
        return postHashTagRepository.findByPostId(post.getId());
    }




}
