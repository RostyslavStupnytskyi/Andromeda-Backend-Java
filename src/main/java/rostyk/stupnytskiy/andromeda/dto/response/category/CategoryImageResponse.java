package rostyk.stupnytskiy.andromeda.dto.response.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryImageResponse {
    private String image;

    public CategoryImageResponse(String image){
        this.image = image;
    }
}
