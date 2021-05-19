package rostyk.stupnytskiy.andromeda.dto.response.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountResponse;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import java.time.LocalDateTime;

@Getter
@Setter
public class GoodsCartItemResponse {

    private Long id;
    private String image;
    private String title;
    private Integer count;
    private Long sellerId;
    private Long advertisementId;
    private ParametersValuesPriceCountResponse priceCountResponse;

    @JsonIgnore
    private LocalDateTime date;

    public GoodsCartItemResponse(GoodsCartItem item){
        cartItemToResponse(item);
        this.priceCountResponse = new ParametersValuesPriceCountResponse(item.getValuesPriceCount());
    }

    public GoodsCartItemResponse(GoodsCartItem item, Currency currency){
        cartItemToResponse(item);
        this.priceCountResponse = new ParametersValuesPriceCountResponse(item.getValuesPriceCount(), currency);
    }

    private void cartItemToResponse(GoodsCartItem item) {
        this.id = item.getId();
        this.sellerId = item.getGoodsAdvertisement().getSeller().getId();
        this.title = item.getGoodsAdvertisement().getTitle();
        this.count = item.getCount();
        this.image = item.getGoodsAdvertisement().getMainImage();
        this.advertisementId = item.getGoodsAdvertisement().getId();
        this.date = item.getDate();
    }
}
