package rostyk.stupnytskiy.andromeda.dto.request.account.goods_seller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsSellerDataRequest {

    private Boolean onlySellerCountryDelivery;

    private List<String> countryCodes;

    private List<Long> deliveryTypesId;

    private String shopName;

    private String avatar;

    private Boolean sendNewOrderNotifications;

    private Boolean sendOrderReceivedNotifications;
}
