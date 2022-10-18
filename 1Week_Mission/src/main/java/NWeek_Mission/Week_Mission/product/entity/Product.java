package NWeek_Mission.Week_Mission.product.entity;

import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import NWeek_Mission.Week_Mission.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Product extends BaseEntity {
    @OneToOne
    private Member author;
    private String subject;
    private Long price;
}
