package rostyk.stupnytskiy.andromeda.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.DeliveryTypeResponse;

import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountResponse;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItemStatus;


@Getter
@Setter
public class GoodsOrderItemResponse {

    private Long id;
    private Integer count;
    private GoodsOrderItemStatus status;
    @JsonProperty("advertisementId")
    private Long goodsAdvertisementId;
    @JsonProperty("description")
    private String descriptionFromUser;
    private DeliveryTypeResponse delivery;

    private ParametersValuesPriceCountResponse priceCountResponse;

    private String image;
    private String title;
    private Double price;


    public GoodsOrderItemResponse(GoodsOrderItem item) {
     this.id = item.getId();
     this.count = item.getCount();
     this.status = item.getStatus();
     this.goodsAdvertisementId = item.getGoodsAdvertisement().getId();
     this.descriptionFromUser = item.getDescriptionFromUser();
//     this.delivery = new DeliveryTypeResponse(item.getDeliveryType());
     this.title = item.getGoodsAdvertisement().getTitle();
     this.image = item.getGoodsAdvertisement().getMainImage();
     this.price = item.getPrice();
     this.priceCountResponse = new ParametersValuesPriceCountResponse(item.getParametersValuesPriceCount());
    }
}
