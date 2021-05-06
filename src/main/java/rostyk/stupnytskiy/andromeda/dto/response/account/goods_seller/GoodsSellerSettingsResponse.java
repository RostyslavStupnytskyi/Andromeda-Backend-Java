package rostyk.stupnytskiy.andromeda.dto.response.account.goods_seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerSettings;

@Getter
@Setter
public class GoodsSellerSettingsResponse {

    private Boolean sendNewOrderNotifications;

    private Boolean sendOrderReceivedNotifications;

    public GoodsSellerSettingsResponse(GoodsSellerSettings settings) {
        this.sendNewOrderNotifications = settings.getSendNewOrderNotifications();
        this.sendOrderReceivedNotifications = settings.getSendOrderReceivedNotifications();
    }
}
