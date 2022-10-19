package NWeek_Mission.Week_Mission.posthashtag.entity;

import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.postkeyword.PostKeyword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class PostHashTag extends BaseEntity {
    @ManyToOne
    private Member member;
    @ManyToOne
    private Post post;
    @ManyToOne
    private PostKeyword postKeyword;

}
