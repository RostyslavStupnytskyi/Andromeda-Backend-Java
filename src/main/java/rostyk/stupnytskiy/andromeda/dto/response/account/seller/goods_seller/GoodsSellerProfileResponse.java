package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller.GoodsSellerStatisticsResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;

@Getter
@Setter
public class GoodsSellerProfileResponse {
    private Long id;
    private String name;
    private String avatar;

    public GoodsSellerProfileResponse(GoodsSellerAccount goodsSellerAccount){
        this.id = goodsSellerAccount.getId();
        this.name = goodsSellerAccount.getShopName();
        this.avatar = goodsSellerAccount.getAvatar();
    }
}
