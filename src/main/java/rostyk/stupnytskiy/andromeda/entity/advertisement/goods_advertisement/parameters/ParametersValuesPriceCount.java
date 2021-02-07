package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class ParametersValuesPriceCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToMany
//    private List<ParameterValue> values;

    @ManyToMany
    @MapKeyJoinColumn(name = "parameter_id")
    private Map<Parameter, ParameterValue> values;

    @OneToMany(mappedBy = "valuesPriceCount")
    private List<GoodsCartItem> goodsCartItems;

    @OneToMany(mappedBy = "valuesPriceCount")
    private List<GoodsOrderItem> goodsOrderItems;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    private Double price;

    private Integer count;
}
