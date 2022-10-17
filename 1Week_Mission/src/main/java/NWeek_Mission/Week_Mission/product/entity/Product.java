package NWeek_Mission.Week_Mission.product.entity;


import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product extends BaseEntity {
    private String subject;
    private String price;
}
