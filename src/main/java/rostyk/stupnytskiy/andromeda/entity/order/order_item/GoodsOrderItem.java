package rostyk.stupnytskiy.andromeda.entity.order.order_item;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer count;

    private GoodsOrderItemStatus status;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    private String descriptionFromUser;

    @ManyToOne
    private DeliveryType deliveryType;

    @ManyToOne
    private GoodsOrder goodsOrder;

    @OneToOne(mappedBy = "goodsOrderItem")
    private GoodsAdvertisementFeedback goodsAdvertisementFeedback;
}
