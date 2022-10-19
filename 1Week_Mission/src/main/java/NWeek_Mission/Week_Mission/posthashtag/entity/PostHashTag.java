package NWeek_Mission.Week_Mission.posthashtag.entity;

import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import NWeek_Mission.Week_Mission.member.entity.Member;
import NWeek_Mission.Week_Mission.post.entity.Post;
import NWeek_Mission.Week_Mission.postkeyword.PostKeyword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class PostHashTag extends BaseEntity {
    @ManyToOne
    private Member member;
    @ManyToOne
    @JoinColumn(
            name="post_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_POST_ID",
                    foreignKeyDefinition = "FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE"
            )
    )
    private Post post;
    @ManyToOne
    @JoinColumn(
            name="post_keyword_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_POST_KEYWORD_ID",
                    foreignKeyDefinition = "FOREIGN KEY (post_keyword_id) REFERENCES post_keyword(id) ON DELETE CASCADE"
            )
    )
    private PostKeyword postKeyword;

}
