package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;

@Getter
@Setter
public class SubcategoryResponse {
    private Long id;
    private String title;
    private Long categoryId;

    public SubcategoryResponse(Subcategory subcategory){
        this.id = subcategory.getId();
        this.title = subcategory.getTitle();
        this.categoryId = subcategory.getCategory().getId();
    }
}
