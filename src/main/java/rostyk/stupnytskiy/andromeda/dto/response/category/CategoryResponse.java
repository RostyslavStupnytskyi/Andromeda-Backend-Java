package rostyk.stupnytskiy.andromeda.dto.response.category;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.Category;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String title;

    public CategoryResponse(Category category){
        this.id = category.getId();
        this.title = category.getTitle();
    }
}
