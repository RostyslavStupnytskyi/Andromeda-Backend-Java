package rostyk.stupnytskiy.andromeda.entity.order;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItemStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;

    private LocalDateTime closingDate;

    private GoodsOrderStatus orderStatus;

    private Boolean isViewed;

    private Double sum;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount user;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsSellerAccount seller;

    @OneToMany(mappedBy = "goodsOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GoodsOrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private GoodsOrderDeliveryDetails deliveryDetails;

    @OneToOne(mappedBy = "goodsOrder", fetch = FetchType.LAZY)
    private GoodsSellerFeedback goodsSellerFeedback;

    @OneToOne(cascade = CascadeType.ALL)
    private GoodsOrderPaymentDetails paymentDetails;

    public boolean isAllGoodsOrderItemsDelivered() {
        for (GoodsOrderItem orderItem : orderItems) {
            if (!orderItem.getStatus().equals(GoodsOrderItemStatus.WAITING_FOR_FEEDBACK)) return false;
        }
        return true;
    }
}
