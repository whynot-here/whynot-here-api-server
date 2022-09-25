package handong.whynot.dto.category;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo implements Comparable<CategoryInfo> {

    private Long parentId;
    private String parentCode;
    private String parentName;
    private List<CategoryDTO> children;


    @Override
    public int compareTo(CategoryInfo info) {

        if (info.parentId < parentId) {
            return 1;
        }
        if (info.parentId > parentId) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CategoryInfo && compareTo((CategoryInfo) o) == 0;
    }
}
