package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.discounts;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CheckDiscountCreatingResponse {
    private Boolean canCreate;
    private DiscountResponse conflictDiscount;
}
