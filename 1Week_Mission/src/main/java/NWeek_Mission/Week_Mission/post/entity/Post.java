package NWeek_Mission.Week_Mission.post.entity;


import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {
    private String subject;
    private String content;
    private String contentHtml;
}
