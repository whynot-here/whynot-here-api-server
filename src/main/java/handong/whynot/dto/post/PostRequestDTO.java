package handong.whynot.dto.post;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {

    private String title;
    private String content;
    private String postImg;

    @Builder.Default
    public List<Long> jobIds = new ArrayList<>();
}
