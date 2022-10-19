package NWeek_Mission.Week_Mission.post.repository;

import NWeek_Mission.Week_Mission.Util;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.posthashtag.entity.PostHashTag;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static NWeek_Mission.Week_Mission.post.entity.QPost.post;
import static NWeek_Mission.Week_Mission.posthashtag.entity.QPostHashTag.postHashTag;
import static NWeek_Mission.Week_Mission.postkeyword.QPostKeyword.postKeyword;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Post> searchQsl(String kw) {
        JPAQuery<Post> jpaQuery = jpaQueryFactory
                .select(post)
                .distinct()
                .from(post);

        if (!Util.str.empty(kw)){
            jpaQuery.innerJoin(postHashTag)
                    .on(post.eq(postHashTag.post))
                    .innerJoin(postKeyword)
                    .on(postKeyword.eq(postHashTag.postKeyword))
                    .where(postKeyword.content.eq(kw));
        } else {

        }
        return jpaQuery.fetch();
    }
}
