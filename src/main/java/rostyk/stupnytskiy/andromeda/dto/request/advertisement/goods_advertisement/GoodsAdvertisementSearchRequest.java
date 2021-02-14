package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;

@Getter
@Setter
public class GoodsAdvertisementSearchRequest {

    private String title;
    private String fromCountryCode;
    private Boolean image;
    private Boolean rating;

    @JsonProperty("currency")
    private String currencyCode;

    @JsonProperty("pagination")
    private PaginationRequest paginationRequest;

}
