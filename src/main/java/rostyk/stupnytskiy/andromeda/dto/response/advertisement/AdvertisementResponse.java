package rostyk.stupnytskiy.andromeda.dto.response.advertisement;

import lombok.Getter;
import lombok.Setter;
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
    private List<String> images;

    private RetailPriceResponse retailPrice;
    private WholesalePriceResponse wholesalePrice;

    private CurrencyResponse currency;

    private SubcategoryResponse subcategory;
    private CategoryResponse category;


    public AdvertisementResponse(Advertisement advertisement) { // TODO
        this.id = advertisement.getId();

        if (advertisement.getIsRetail())
            this.retailPrice = new RetailPriceResponse(advertisement.getCurrentRetailPrice());
        else this.wholesalePrice = new WholesalePriceResponse(advertisement.getCurrentWholeSalePrice());

        this.currency = new CurrencyResponse(advertisement.getCurrency());
        this.title = advertisement.getTitle();
        this.description = advertisement.getDescription();
        this.mainImage = advertisement.getMainImage();
        this.images = advertisement.getImages();

        if(advertisement.getCategory() != null) this.category = new CategoryResponse(advertisement.getCategory());
        else this.subcategory = new SubcategoryResponse(advertisement.getSubcategory());
        
//        this.sellerId = advertisement.getSeller().getId();
//        this.images = advertisement.getImages();
    }
}
