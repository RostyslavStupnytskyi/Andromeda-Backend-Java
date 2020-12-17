package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import java.util.List;

@Getter
@Setter
public class AdvertisementResponse {
    private Long id;
    private String title;
    private String description;
    private String mainImage;
    private List<String> images;
    private SubcategoryResponse subcategory;
    private Double rating;
    private Integer price;
    private Long allViews;
    private Long userViews;
    private Long sellerId;

    public AdvertisementResponse(Advertisement advertisement) { // TODO
        this.id = advertisement.getId();
        this.title = advertisement.getTitle();
        this.description = advertisement.getDescription();
        this.mainImage = advertisement.getMainImage();
        this.subcategory = new SubcategoryResponse(advertisement.getSubcategory());
//        this.price = advertisement.getPrice();
        this.sellerId = advertisement.getSeller().getId();
        this.images = advertisement.getImages();
    }
}
