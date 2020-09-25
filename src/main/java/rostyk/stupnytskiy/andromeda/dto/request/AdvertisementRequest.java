package rostyk.stupnytskiy.andromeda.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdvertisementRequest {


    private String title;

    private String description;

    private String mainImage;

    private List<String> images;

    private Long subcategoryId;

    private Integer price;
}
