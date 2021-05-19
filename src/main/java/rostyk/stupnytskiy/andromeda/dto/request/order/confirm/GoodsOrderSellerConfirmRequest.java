package rostyk.stupnytskiy.andromeda.dto.request.order.confirm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderPaymentRequest;

import java.util.List;

@Getter
@Setter
public class GoodsOrderSellerConfirmRequest {
    private Double sum;

    @JsonProperty("delivery")
    private Long deliveryTypeId;

    private GoodsOrderPaymentRequest payment;

}
