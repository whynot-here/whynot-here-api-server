package handong.whynot.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class PostRequestDTO {

    public Long userId;
    public String title;
    public String content;
    public String postImg;

    @Builder.Default
    public List<Long> jobIds = new ArrayList<>();
}
