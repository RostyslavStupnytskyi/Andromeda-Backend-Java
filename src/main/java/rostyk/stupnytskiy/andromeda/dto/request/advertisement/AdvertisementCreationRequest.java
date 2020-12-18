package rostyk.stupnytskiy.andromeda.dto.request.advertisement;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AdvertisementCreationRequest {

    private String title;
    private String description;
    private String mainImage;
    private List<String> images;

    private Boolean onlySellerCountry;
    private Boolean isRetail;

    private Long categoryId;

    private Long subcategoryId;

    private List<PropertyRequest> properties;

    private RetailPriceRequest retailPriceRequest;

    private WholesalePriceRequest wholesalePriceRequest;

    @Override
    public String toString() {
        return "AdvertisementCreationRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", onlySellerCountry=" + onlySellerCountry +
                ", isRetail=" + isRetail +
                ", images=" + images +
                ", categoryId=" + categoryId +
                ", subcategoryId=" + subcategoryId +
                ", properties=" + properties +
                ", retailPriceRequest=" + retailPriceRequest +
                ", wholesalePriceRequest=" + wholesalePriceRequest +
                '}';
    }
}
