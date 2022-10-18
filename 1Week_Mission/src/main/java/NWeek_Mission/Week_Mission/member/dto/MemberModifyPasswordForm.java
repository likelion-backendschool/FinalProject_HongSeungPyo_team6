package NWeek_Mission.Week_Mission.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberModifyPasswordForm {

    @NotEmpty(message = "현재 비밀번호는 필수항목입니다.")
    private String oldPassword;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordConfirm;
}
