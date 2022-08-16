package handong.whynot.service;

import handong.whynot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public String initOrder() {
        return categoryRepository.findAll().stream()
                .map(it -> it.getId().toString())
                .collect(Collectors.joining(", "));
    }
}
