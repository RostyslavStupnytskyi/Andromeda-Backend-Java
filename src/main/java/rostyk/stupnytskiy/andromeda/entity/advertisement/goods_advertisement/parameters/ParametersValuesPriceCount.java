package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class ParametersValuesPriceCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @MapKeyJoinColumn(name = "parameter_id")
    private Map<Parameter, ParameterValue> values = new HashMap<>();

    @OneToMany(mappedBy = "valuesPriceCount")
    private List<GoodsCartItem> goodsCartItems = new ArrayList<>();;

    @OneToMany(mappedBy = "valuesPriceCount")
    private List<Discount> discounts = new ArrayList<>();

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    private Double price;

    private Integer count;

    public Discount getCurrentDiscount() {
        Discount discount = null;
        LocalDateTime date = LocalDateTime.now();
        for (Discount d : discounts) {
            if (d.getStartDate().isBefore(date) && d.getEndDate().isAfter(date)) {
                discount = d;
            }
        }
        return discount;
    }

    public boolean hasFutureDiscounts() {
        return this.discounts.stream().anyMatch((d) -> d.getEndDate().isAfter(LocalDateTime.now()));
    }

    public boolean hasDiscount() {
        return getCurrentDiscount() != null;
    }

    public Double getPriceWithCurrentDiscount() {
        if (!hasDiscount()) return price;
        else return getCurrentDiscount().getPriceWithDiscount(price);
    }

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
