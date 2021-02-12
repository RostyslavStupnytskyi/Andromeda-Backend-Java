package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Map<Parameter, ParameterValue> values = new HashMap<>();

    @OneToMany(mappedBy = "valuesPriceCount")
    private List<GoodsCartItem> goodsCartItems = new ArrayList<>();;

    @OneToMany(mappedBy = "valuesPriceCount")
    private List<GoodsOrderItem> goodsOrderItems = new ArrayList<>();;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    private Double price;

    private Integer count;

    @Override
    public String toString() {
        return "ParametersValuesPriceCount{" +
                "id=" + id +
                ", goodsAdvertisement=" + goodsAdvertisement.getId() +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
