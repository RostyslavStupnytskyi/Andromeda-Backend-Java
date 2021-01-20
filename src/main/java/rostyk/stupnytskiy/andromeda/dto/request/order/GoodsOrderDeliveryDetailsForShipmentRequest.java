package rostyk.stupnytskiy.andromeda.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GoodsOrderDeliveryDetailsForShipmentRequest {

    @JsonProperty("delivery")
    @NotNull
    private Long deliveryTypeId;

    @NotNull
    @NotBlank
    private String trackingNumber;

    private String sellerMessage;

    @NotNull
    private Long orderId;
}
