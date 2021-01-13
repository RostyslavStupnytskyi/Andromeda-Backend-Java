package rostyk.stupnytskiy.andromeda.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsOrderRequest {

    private List<GoodsOrderItemRequest> items;

    private Long addressId;



}
