package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;

public interface GoodsAdvertisementEntity {
    <T extends GoodsAdvertisementForSearchResponse> GoodsAdvertisementForSearchResponse mapToSearchResponse();

    Double getPriceForSort();
}
