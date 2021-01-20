package rostyk.stupnytskiy.andromeda.dto.response.order;

import lombok.*;

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class SellerGoodsOrdersDataResponse {
    private Long allItems;
    private Long newItems;
    private Long waitingForSending;
    private Long waitingForDelivery;
    private Long closedOrders;

}
