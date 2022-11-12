package handong.whynot.dto.admin;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class UserFeedbackRequestDTO {

    @Min(0) @Max(5)
    private int rating;

    private String description;
}
