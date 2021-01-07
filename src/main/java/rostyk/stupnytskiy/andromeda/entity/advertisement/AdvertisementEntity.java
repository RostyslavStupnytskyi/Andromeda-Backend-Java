package rostyk.stupnytskiy.andromeda.entity.advertisement;


import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;

public interface AdvertisementEntity {
    <T extends AdvertisementResponse> AdvertisementResponse mapToResponse();

}
