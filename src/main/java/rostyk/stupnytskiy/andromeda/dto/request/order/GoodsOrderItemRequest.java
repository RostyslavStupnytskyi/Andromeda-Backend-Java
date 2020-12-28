package rostyk.stupnytskiy.andromeda.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class GoodsOrderItemRequest {

    private Integer count;

    @JsonProperty("advertisement")
    private Long goodsAdvertisementId;

    private String description;

}
