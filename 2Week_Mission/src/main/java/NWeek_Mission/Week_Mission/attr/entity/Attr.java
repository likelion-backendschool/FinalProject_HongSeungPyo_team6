package NWeek_Mission.Week_Mission.attr.entity;

import NWeek_Mission.Week_Mission.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
// @Index(name = "uniqueIndex1", columnList = "relTypeCode, relId, typeCode, type2Code", unique = true) 로 인한 작용 원리
// UPSERT 쿼리가 날라가게 된다.
// UPSERT 쿼리란?
// UPSERT 는 INSERT + UPDATE 를 뜻하는 바이다.
// 하나의 쿼리로 INSERT 를 시도하고, 실패하면 UPDATE 를(중복 된 데이터를 업데이트) 하는 쿼리를 해주는 것이다.
@Table(
        indexes = {
                @Index(name = "uniqueIndex1", columnList = "relTypeCode, relId, typeCode, type2Code", unique = true), // 중복 체크
                @Index(name = "index1", columnList = "relTypeCode, typeCode, type2Code") // relTypeCode, typeCode, type2Code 따른 고속 검색.
        }
)
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Attr extends BaseEntity {
    private String relTypeCode;
    private long relId;
    private String typeCode;
    private String type2Code;
    private String value;
    private LocalDateTime expireDate;
}
