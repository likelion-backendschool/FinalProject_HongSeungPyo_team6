package NWeek_Mission.Week_Mission.member.entity;

import lombok.Getter;
// 유저의 역할 : ADMIN, USER
@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}