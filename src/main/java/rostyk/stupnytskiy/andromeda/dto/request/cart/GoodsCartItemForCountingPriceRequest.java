package rostyk.stupnytskiy.andromeda.dto.request.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsCartItemForCountingPriceRequest {
    private Long advertisementId;
    private Integer count;
}
