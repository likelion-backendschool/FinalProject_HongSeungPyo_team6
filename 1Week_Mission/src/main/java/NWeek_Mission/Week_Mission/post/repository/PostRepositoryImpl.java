package NWeek_Mission.Week_Mission.post.repository;

import NWeek_Mission.Week_Mission.Util;
import NWeek_Mission.Week_Mission.member.dto.MemberContext;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.posthashtag.entity.PostHashTag;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static NWeek_Mission.Week_Mission.member.entity.QMember.member;
import static NWeek_Mission.Week_Mission.post.entity.QPost.post;
import static NWeek_Mission.Week_Mission.posthashtag.entity.QPostHashTag.postHashTag;
import static NWeek_Mission.Week_Mission.postkeyword.entity.QPostKeyword.postKeyword;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Post> searchQsl(String kw, MemberContext memberContext) {
        JPAQuery<Post> jpaQuery = jpaQueryFactory
                .select(post)
                .distinct()
                .from(post);

        // 로그인이 안된 경우 == memberContext 가 null 인 경우
        if (!Util.str.empty(kw) && !(memberContext == null)){
            jpaQuery.innerJoin(postHashTag)
                    .on(post.eq(postHashTag.post))
                    .innerJoin(postKeyword)
                    .on(postKeyword.eq(postHashTag.postKeyword))
                    .where(postKeyword.content.eq(kw).and(postHashTag.member.id.eq(memberContext.getId())));
        }
        return jpaQuery.fetch();
    }
}
