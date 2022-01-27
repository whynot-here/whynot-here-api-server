package handong.whynot.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PostRequestDTO {

    public String title;
    public String postImg;
    public String content;

}
