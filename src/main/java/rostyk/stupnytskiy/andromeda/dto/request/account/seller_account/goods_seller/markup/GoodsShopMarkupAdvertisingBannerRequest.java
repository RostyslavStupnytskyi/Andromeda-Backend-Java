package rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodsShopMarkupAdvertisingBannerRequest {

    private List<String> images = new ArrayList<>();
    private Integer line;
    private Integer position;
}
