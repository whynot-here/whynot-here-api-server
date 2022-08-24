package handong.whynot.dto.category;

import handong.whynot.domain.Category;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String code;
    private String name;
    private String parentCode;

    public static CategoryDTO generateDTOBy(Category category) {
        return builder()
                .id(category.getId())
                .code(category.getCode())
                .name(category.getName())
                .parentCode(category.getParentCode())
                .build();
    }
}
