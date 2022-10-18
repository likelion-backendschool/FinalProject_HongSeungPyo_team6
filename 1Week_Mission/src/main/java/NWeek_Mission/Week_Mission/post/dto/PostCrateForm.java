package NWeek_Mission.Week_Mission.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostCrateForm {
    @NotEmpty
    private String subject;
    @NotEmpty
    private String content;

    @NotEmpty
    private String postKeywordContents;
}
