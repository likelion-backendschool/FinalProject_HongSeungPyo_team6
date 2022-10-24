package NWeek_Mission.Week_Mission.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFindUsernameForm {

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String email;
}
