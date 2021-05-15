package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.CORBA.Current;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

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
    private List<GoodsCartItem> goodsCartItems = new ArrayList<>();
    ;

    @OneToMany(mappedBy = "valuesPriceCount")
    private List<Discount> discounts = new ArrayList<>();

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    @OneToMany(mappedBy = "parametersValuesPriceCount")
    private List<ParameterValuesCurrencyPrice> prices;

    private Integer count;

    public Double getPriceByCurrency(String code) {
        return this.prices.stream().filter((p) -> p.getCurrency().getCode().equals(code)).findFirst().get().getPrice();
    }

    public Double getPriceByCurrency(Currency currency) {
        if (currency != null)
            return this.prices.stream().filter((p) -> p.getCurrency() == currency).findFirst().get().getPrice();
        else return getPriceByCurrency("USD");
    }

    public Double getPriceWithCurrentDiscount(Currency currency) {
        if (!hasDiscount()) return getPriceByCurrency(currency);
        else return getCurrentDiscountOrReturnNull().getPriceWithDiscount(getPriceByCurrency(currency));
    }

    public Double getPriceWithCurrentDiscount(String code) {
        if (!hasDiscount()) return getPriceByCurrency(code);
        else return getCurrentDiscountOrReturnNull().getPriceWithDiscount(getPriceByCurrency(code));
    }


    public boolean hasFutureDiscounts() {
        return this.discounts.stream().anyMatch((d) -> d.getEndDate().isAfter(LocalDateTime.now()));
    }

    public Discount getCurrentDiscountOrReturnNull() {
        Discount discount = null;
        LocalDateTime date = LocalDateTime.now();
        for (Discount d : discounts) {
            if (d.getStartDate().isBefore(date) && d.getEndDate().isAfter(date)) {
                discount = d;
            }
        }
        return discount;
    }

    public boolean hasDiscount() {
        return getCurrentDiscountOrReturnNull() != null;
    }

    public boolean hasCurrency(Currency currency) {
        return this.prices.stream().anyMatch((p) -> p.getCurrency() == currency);
    }

    public boolean hasCurrency(String code) {
        return this.prices.stream().anyMatch((p) -> p.getCurrency().getCode().equals(code));
    }

    @Override
    public String toString() {
        return "ParametersValuesPriceCount{" +
                "id=" + id +
                ", goodsAdvertisement=" + goodsAdvertisement.getId() +
                ", count=" + count +
                '}';
    }
}
