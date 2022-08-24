package handong.whynot.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryInfo implements Comparable<CategoryInfo>{

    private Long parentId;
    private String parentCode;
    private String parentName;
    private List<CategoryDTO> children;


    @Override
    public int compareTo(CategoryInfo info) {

        if(info.parentId < parentId) {
            return 1;
        } else if(info.parentId > parentId) {
            return -1;
        }
        return 0;
    }
}
