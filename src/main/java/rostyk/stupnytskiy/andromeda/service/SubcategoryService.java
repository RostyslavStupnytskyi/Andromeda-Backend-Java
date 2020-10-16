package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.SubcategoryRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.SubcategoryResponse;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;
import rostyk.stupnytskiy.andromeda.repository.SubcategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public void save(SubcategoryRequest request){
        subcategoryRepository.save(subcategoryRequestToSubcategory(request,null));
    }
    public void update(SubcategoryRequest request, Long id){
        subcategoryRepository.save(subcategoryRequestToSubcategory(request,findOneById(id)));
    }

    public List<SubcategoryResponse> findAll(){
        return subcategoryRepository.findAll()
                .stream()
                .map(SubcategoryResponse::new)
                .collect(Collectors.toList());
    }

    public List<SubcategoryResponse> findAllByCategoryId(Long id){
        return subcategoryRepository.findAllByCategoryId(id)
                .stream()
                .map(SubcategoryResponse::new)
                .collect(Collectors.toList());
    }

    public PageResponse<SubcategoryResponse> findPageByCategoryId(Long id, PaginationRequest request){
        final Page<Subcategory> page = subcategoryRepository.findAllByCategoryId(id, request.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(SubcategoryResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void delete (Long id){
        subcategoryRepository.delete(findOneById(id));
    }

    public Subcategory findOneById(Long id){
        return subcategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subcategory with id " + id + " not exist"));
    }

    private Subcategory subcategoryRequestToSubcategory(SubcategoryRequest request, Subcategory subcategory) {
        if (subcategory == null) {
            subcategory = new Subcategory();
        }
        subcategory.setCategory(categoryService.findById(request.getCategoryId()));
        subcategory.setTitle(request.getTitle());
        return subcategory;
    }
}
