package handong.whynot.dto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostImageLinkDTO {

    private Long id;

    @NotBlank
    private String link;

    public static PostImageLinkDTO of(Long id, String link) {
        return builder()
                .id(id)
                .link(link)
                .build();
    }
}
