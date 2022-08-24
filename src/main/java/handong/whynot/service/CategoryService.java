package handong.whynot.service;

import handong.whynot.domain.Category;
import handong.whynot.dto.category.CategoryDTO;
import handong.whynot.dto.category.CategoryInfo;
import handong.whynot.repository.CategoryQueryRepository;
import handong.whynot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    public String initOrder() {

        return categoryRepository.findAll().stream()
                .map(it -> it.getId().toString())
                .collect(Collectors.joining(", "));
    }

    public List<CategoryInfo> getDefaultList() {

        List<CategoryInfo> responseList = new ArrayList<>();
        List<Category> categoryList = categoryQueryRepository.getDefaultList();

        categoryList.stream()
                .collect(Collectors.groupingByConcurrent(Category::getParentCode))
                .forEach((key, value) -> {

                    Category category = value.get(0);
                    CategoryInfo info = CategoryInfo.builder()
                            .parentId(category.getId())
                            .parentCode(category.getCode())
                            .parentName(category.getName())
                            .build();

                    List<CategoryDTO> children = value.subList(1, value.size()).stream()
                            .map(CategoryDTO::generateDTOBy)
                            .collect(Collectors.toList());
                    info.setChildren(children);

                    responseList.add(info);
                });

        Collections.sort(responseList);

        return responseList;
    }
}
