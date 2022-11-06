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

        return parentId.compareTo(info.parentId);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CategoryInfo && compareTo((CategoryInfo) o) == 0;
    }

    @Override
    public int hashCode() {
        return parentId != null ? parentId.hashCode() : 0;
    }
}
