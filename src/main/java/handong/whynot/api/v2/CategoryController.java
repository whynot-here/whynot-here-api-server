package handong.whynot.api.v2;

import handong.whynot.dto.category.CategoryInfo;
import handong.whynot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/default")
    @Cacheable("DefaultCategory")
    public List<CategoryInfo> getDefaultList() {

        return categoryService.getDefaultList();
    }
}
