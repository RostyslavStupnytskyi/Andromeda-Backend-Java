package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;

@Getter
@Setter
public class SubcategoryResponse {
    private String title;

    public SubcategoryResponse(Subcategory subcategory){
        this.title = subcategory.getTitle();
    }
}
