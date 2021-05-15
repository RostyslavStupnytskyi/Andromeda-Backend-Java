package rostyk.stupnytskiy.andromeda.dto.request.order;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentMethod;

@Getter
@Setter
public class GoodsOrderPaymentRequest {
    private GoodsOrderPaymentMethod method;
    private String currency;
}
