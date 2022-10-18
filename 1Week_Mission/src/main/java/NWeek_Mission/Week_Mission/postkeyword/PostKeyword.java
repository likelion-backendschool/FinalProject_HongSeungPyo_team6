package NWeek_Mission.Week_Mission.postkeyword;

import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class PostKeyword extends BaseEntity {
    private String content;
}
