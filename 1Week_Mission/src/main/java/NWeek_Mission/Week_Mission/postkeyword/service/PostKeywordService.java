package NWeek_Mission.Week_Mission.postkeyword.service;

import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.postkeyword.PostKeyword;
import NWeek_Mission.Week_Mission.postkeyword.repository.PostKeywordRepository;
import javassist.compiler.ast.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostKeywordService {

    private final PostKeywordRepository postKeywordRepository;
    public PostKeyword save(String keywordContent) {
        Optional<PostKeyword> optKeyword = postKeywordRepository.findByContent(keywordContent);

        if (optKeyword.isPresent()){
            return optKeyword.get();
        }

        PostKeyword postKeyword = PostKeyword
                .builder()
                .content(keywordContent)
                .build();
        postKeywordRepository.save(postKeyword);
        return postKeyword;
    }


}
