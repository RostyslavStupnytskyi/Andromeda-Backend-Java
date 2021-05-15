package rostyk.stupnytskiy.andromeda.entity.order.order_item;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.country.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
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
    private Double price;
    @ManyToOne
    private ParametersValuesPriceCount parametersValuesPriceCount;
    private String descriptionFromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsOrder goodsOrder;

    @OneToOne(mappedBy = "goodsOrderItem")
    private GoodsAdvertisementFeedback goodsAdvertisementFeedback;
}
