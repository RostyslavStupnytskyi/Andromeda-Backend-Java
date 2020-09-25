package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.CategoryRequest;
import rostyk.stupnytskiy.andromeda.dto.response.CategoryResponse;
import rostyk.stupnytskiy.andromeda.entity.Category;
import rostyk.stupnytskiy.andromeda.repository.CategoryRepository;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileTool fileTool;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    public void save(CategoryRequest categoryRequest) {
        categoryRepository.save(categoryRequestToCategory(categoryRequest, null));
    }

    public void update(CategoryRequest categoryRequest, Long id) {
        categoryRepository.save(categoryRequestToCategory(categoryRequest, findById(id)));
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " not exist"));
    }

    public void delete(Long id) {
        categoryRepository.delete(findById(id));
    }

    private Category categoryRequestToCategory(CategoryRequest categoryRequest, Category category) {
        if (category == null) {
            category = new Category();
        }
        if (categoryRequest.getImage() != null) {
            try {
                category.setImage(fileTool.saveCategoryImage(categoryRequest.getImage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        category.setTitle(categoryRequest.getTitle());
        return category;
    }
}
