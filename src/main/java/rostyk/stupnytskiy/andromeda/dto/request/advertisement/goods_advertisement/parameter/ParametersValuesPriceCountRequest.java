package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParameterValue;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ParametersValuesPriceCountRequest {

    private Map<String, String> valueParam;

    private Double price;

    private Integer count;
}
