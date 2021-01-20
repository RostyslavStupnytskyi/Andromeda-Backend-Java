package rostyk.stupnytskiy.andromeda.dto.response.order;

import lombok.*;

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class UserGoodsOrdersDataResponse {
    private Long allItems;
    private Long waitingForSending;
    private Long waitingForDelivery;
    private Long closedOrders;

}
