package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row.GoodsShopMarkupAdvertisementRow;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row.GoodsShopMarkupAdvertisementRowType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GoodsShopMarkupAdvertisementRowResponse {
    private Long id;

    private GoodsShopMarkupAdvertisementRowType rowType;

    private List<GoodsAdvertisementForSearchResponse> advertisements;

    public GoodsShopMarkupAdvertisementRowResponse(GoodsShopMarkupAdvertisementRow row, List<GoodsAdvertisement> advertisements) {
        this.id = row.getId();
        this.rowType = row.getRowType();
        this.advertisements = advertisements.stream().map(GoodsAdvertisementForSearchResponse::new).collect(Collectors.toList());
    }
}
