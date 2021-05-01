package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.account.user.UserSettingsResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.account.user.UserStatisticsResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;

@Getter
@Setter
public class GoodsSellerDataResponse {
    private Long id;
    private String shopName;
    private String avatar;

    private GoodsSellerSettingsResponse settings;

    public GoodsSellerDataResponse(GoodsSellerAccount seller){
        this.id = seller.getId();
        this.shopName = seller.getShopName();
        this.avatar = seller.getAvatar();
        this.settings = new GoodsSellerSettingsResponse(seller.getSettings());
    }
}
