package NWeek_Mission.Week_Mission.member.entity;

import lombok.Getter;
// 유저의 역할 : ADMIN(권한 레벨 : 7), MEMBER(권한 레벨 : 4), WRITER(권한 레벨 : 3)
@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),

    WRITER("ROLE_WRITER");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}