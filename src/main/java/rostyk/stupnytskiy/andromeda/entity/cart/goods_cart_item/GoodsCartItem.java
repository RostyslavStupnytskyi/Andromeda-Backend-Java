package rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.country.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
import rostyk.stupnytskiy.andromeda.entity.cart.CartItem;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@DiscriminatorValue("goods_item")
public class GoodsCartItem extends CartItem {

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsAdvertisement goodsAdvertisement;

    @ManyToOne
    private ParametersValuesPriceCount valuesPriceCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryType deliveryType;

    @NotNull
    private Integer count = 1;

    @Override
    public String toString() {
        return "GoodsCartItem{" +
                "goodsAdvertisement=" + goodsAdvertisement.getId()  +
                ", count=" + count +
                '}';
    }
}
