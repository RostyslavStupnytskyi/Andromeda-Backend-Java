package rostyk.stupnytskiy.andromeda.dto.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodsOrderResponse {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime closingDate;

    private GoodsOrderStatus orderStatus;

    private Boolean isViewed;

    private List<GoodsOrderItemResponse> items = new ArrayList<>();

    private GoodsOrderDeliveryDetailsResponse deliveryDetails;

    private Double price;

    private String seller;
    private Long sellerId;

    public GoodsOrderResponse(GoodsOrder goodsOrder) {
        this.id = goodsOrder.getId();
        this.creationDate = goodsOrder.getCreationDate();
        this.orderStatus = goodsOrder.getOrderStatus();
        this.isViewed = goodsOrder.getIsViewed();
        this.deliveryDetails = new GoodsOrderDeliveryDetailsResponse(goodsOrder.getDeliveryDetails());
        this.seller = goodsOrder.getSeller().getShopName();
        this.sellerId = goodsOrder.getSeller().getId();
        goodsOrder.getOrderItems().forEach((i) -> this.items.add(new GoodsOrderItemResponse(i)));
        this.price = goodsOrder.getPrice();
        this.closingDate = goodsOrder.getClosingDate();
    }

}
