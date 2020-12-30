package rostyk.stupnytskiy.andromeda.dto.request.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
public class GoodsAdvertisementFeedbackRequest {

    @JsonProperty("advertisement")
    private Long goodsAdvertisementId;

    @JsonProperty("order_item")
    private Long goodsOrderItemId;

    private Double rating;

    private String text;

    private String image;
}
