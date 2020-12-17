package rostyk.stupnytskiy.andromeda.dto.request.delivery;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellerDeliveryRequest {
    private List<Long> deliveriesId;
}
