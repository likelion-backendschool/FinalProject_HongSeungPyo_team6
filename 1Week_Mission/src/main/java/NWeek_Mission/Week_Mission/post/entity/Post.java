package NWeek_Mission.Week_Mission.post.entity;


import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import NWeek_Mission.Week_Mission.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {
    private String subject;
    private String content;
    private String contentHtml;

    @ManyToOne
    private Member author;
}
