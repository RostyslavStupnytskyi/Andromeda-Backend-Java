package rostyk.stupnytskiy.andromeda.dto.response.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.retail.RetailPriceResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.wholesale.WholesalePriceResponse;
import rostyk.stupnytskiy.andromeda.dto.response.category.SubcategoryResponse;
import rostyk.stupnytskiy.andromeda.dto.response.category.CategoryResponse;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import java.util.List;

@Getter
@Setter
public class AdvertisementResponse {

    private Long id;

    private String title;

    private String description;

    private String mainImage;

    private String type;


    public AdvertisementResponse(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.title = advertisement.getTitle();
        this.description = advertisement.getDescription();
        this.mainImage = advertisement.getMainImage();
        this.type = advertisement.getDiscriminatorValue();

    }
}
