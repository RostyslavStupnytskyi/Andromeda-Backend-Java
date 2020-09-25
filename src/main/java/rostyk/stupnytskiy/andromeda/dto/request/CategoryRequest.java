package rostyk.stupnytskiy.andromeda.dto.request;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.Category;

@Getter
@Setter
public class CategoryRequest {
    private String title;
    private String image;


}
