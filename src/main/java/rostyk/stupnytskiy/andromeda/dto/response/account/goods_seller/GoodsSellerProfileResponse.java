package rostyk.stupnytskiy.andromeda.dto.response.account.goods_seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;

@Getter
@Setter
public class GoodsSellerProfileResponse {
    private Long id;
    private String name;
    private String avatar;
    private String banner;
    private String description;

    public GoodsSellerProfileResponse(GoodsSellerAccount goodsSellerAccount){
        this.id = goodsSellerAccount.getId();
        this.name = goodsSellerAccount.getShopName();
        this.avatar = goodsSellerAccount.getAvatar();
        this.banner = goodsSellerAccount.getSettings().getBannerImage();
        this.description = goodsSellerAccount.getDescription();
    }
}
