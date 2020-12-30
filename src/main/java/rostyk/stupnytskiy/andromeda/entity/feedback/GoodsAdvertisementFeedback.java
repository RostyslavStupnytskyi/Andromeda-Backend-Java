package rostyk.stupnytskiy.andromeda.entity.feedback;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsAdvertisementFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    @OneToOne
    private GoodsOrderItem goodsOrderItem;

    private Double rating;

    private String text;

    private String image;

    @ManyToOne
    private Country country;
}
