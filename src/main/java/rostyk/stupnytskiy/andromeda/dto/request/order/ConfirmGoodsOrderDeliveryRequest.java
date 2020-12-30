package rostyk.stupnytskiy.andromeda.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ConfirmGoodsOrderDeliveryRequest {

    @NotNull
    @JsonProperty("order")
    private Long orderId;

    private List<Long> orderItems;
}
