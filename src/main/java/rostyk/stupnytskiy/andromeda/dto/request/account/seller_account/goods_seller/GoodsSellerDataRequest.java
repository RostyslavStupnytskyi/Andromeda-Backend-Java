package rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.SellerDataRequest;
import java.util.List;

@Getter
@Setter
public class GoodsSellerDataRequest {

    private Boolean onlySellerCountryDelivery;

    private List<String> countryCodes;

    private List<Long> deliveryTypesId;
}
