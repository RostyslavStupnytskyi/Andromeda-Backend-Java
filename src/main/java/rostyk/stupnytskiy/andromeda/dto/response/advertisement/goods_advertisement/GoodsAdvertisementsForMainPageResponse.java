package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.category.CategoryResponse;

@Getter
@Setter
public class GoodsAdvertisementsForMainPageResponse {

    private CategoryResponse category;

    private PageResponse<GoodsAdvertisementForSearchResponse> responses;

}
