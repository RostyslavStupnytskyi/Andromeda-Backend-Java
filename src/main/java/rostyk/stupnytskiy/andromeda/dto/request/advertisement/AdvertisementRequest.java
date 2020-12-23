package rostyk.stupnytskiy.andromeda.dto.request.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

@Getter
@Setter
public class AdvertisementRequest {
    private String title;
    private String description;
    private String mainImage;
}
