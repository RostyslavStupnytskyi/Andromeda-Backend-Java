package rostyk.stupnytskiy.andromeda.entity.order;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
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

    private GoodsOrderStatus orderStatus;

    @ManyToOne
    private UserAccount user;

    @ManyToOne
    private GoodsSellerAccount seller;

    @ManyToOne
    private DeliveryType deliveryType;

    @OneToMany(mappedBy = "goodsOrder", cascade = CascadeType.ALL)
    private List<GoodsOrderItem> orderItems = new ArrayList<>();

    @OneToOne
    private GoodsOrderDeliveryDetails deliveryDetails;


    public boolean didAllGoodsOrderItemsDelivered() {
        for (GoodsOrderItem orderItem : orderItems) {
            if (!orderItem.getStatus().equals(GoodsOrderItemStatus.WAITING_FOR_FEEDBACK)) return false;
        }
        return true;
    }
}
