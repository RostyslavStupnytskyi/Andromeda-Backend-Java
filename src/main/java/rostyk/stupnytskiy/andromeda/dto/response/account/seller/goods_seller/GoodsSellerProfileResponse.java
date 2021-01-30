package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.GoodsSellerStatisticsResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;

@Getter
@Setter
public class GoodsSellerProfileResponse {
    private Long id;
    private String name;
    private String avatar;
    private String taxpayerNumber;

    private GoodsSellerStatisticsResponse statistics;

    public GoodsSellerProfileResponse(GoodsSellerAccount goodsSellerAccount){
        this.id = goodsSellerAccount.getId();
        this.name = goodsSellerAccount.getShopName();
        this.avatar = goodsSellerAccount.getAvatar();
        this.taxpayerNumber = goodsSellerAccount.getTaxpayerNumber();

        this.statistics = new GoodsSellerStatisticsResponse(goodsSellerAccount);

    }
}
