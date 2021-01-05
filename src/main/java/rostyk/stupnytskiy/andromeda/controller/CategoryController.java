package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.CategoryRequest;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.response.category.CategoryResponse;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.service.CategoryService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public Long save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }

    @PutMapping()
    public void update(Long id, @RequestBody CategoryRequest request){
        categoryService.update(request,id);
    }

    @GetMapping("/all")
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    @GetMapping()
    public PageResponse<CategoryResponse> findPage(PaginationRequest request){
        return categoryService.findCategoryPage(request);
    }

    @GetMapping("/one")
    public CategoryResponse getOne(Long id){
        return new CategoryResponse(categoryService.findById(id));
    }


    @DeleteMapping
    public void delete(Long id){
        categoryService.delete(id);
    }

}

