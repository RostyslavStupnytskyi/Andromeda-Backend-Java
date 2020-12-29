package rostyk.stupnytskiy.andromeda.entity.order;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToOne(cascade = CascadeType.ALL)
    private UserDeliveryAddress address;

    @ManyToOne
    private UserAccount user;

    @ManyToOne
    private GoodsSellerAccount seller;

    @ManyToOne
    private DeliveryType deliveryType;

    @OneToMany(mappedBy = "goodsOrder")
    private List<GoodsOrderItem> orderItems;

}
