package rostyk.stupnytskiy.andromeda.dto.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.changes.GoodsOrderSellerChangeResponse;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;
import rostyk.stupnytskiy.andromeda.entity.order.changes.GoodsOrderSellerChange;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GoodsOrderResponse {

    private Long id;

    private GoodsOrderStatus orderStatus;
    private Boolean isViewed;

    private List<GoodsOrderItemResponse> items;
    private List<GoodsOrderSellerChangeResponse> changes;

    private GoodsOrderPaymentDetailsResponse paymentDetails;
    private GoodsOrderDeliveryDetailsResponse deliveryDetails;
    private Double price;

    private String seller;
    private Long sellerId;
    private Long chatId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime closingDate;

    public GoodsOrderResponse(GoodsOrder goodsOrder) {
        this.id = goodsOrder.getId();
        this.creationDate = goodsOrder.getCreationDate();
        this.orderStatus = goodsOrder.getOrderStatus();
        this.isViewed = goodsOrder.getIsViewed();
        this.deliveryDetails = new GoodsOrderDeliveryDetailsResponse(goodsOrder.getDeliveryDetails());
        this.seller = goodsOrder.getSeller().getShopName();
        this.sellerId = goodsOrder.getSeller().getId();
        this.items = goodsOrder.getOrderItems().stream().map(GoodsOrderItemResponse::new).collect(Collectors.toList());
        this.changes = goodsOrder.getChanges().stream().map(GoodsOrderSellerChangeResponse::new).collect(Collectors.toList());
        this.price = goodsOrder.getSum();
        this.closingDate = goodsOrder.getClosingDate();
        this.paymentDetails = new GoodsOrderPaymentDetailsResponse(goodsOrder.getPaymentDetails());
        this.chatId = goodsOrder.getChat().getId();
    }

}
