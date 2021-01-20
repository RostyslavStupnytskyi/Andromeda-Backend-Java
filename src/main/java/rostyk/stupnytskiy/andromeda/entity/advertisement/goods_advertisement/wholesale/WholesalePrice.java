package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class WholesalePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "wholesalePrice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WholesalePriceUnit> priceUnits = new ArrayList<>();

    @ManyToOne
    private WholesaleGoodsAdvertisement advertisement;

    public Double getMinPrice() {
        return priceUnits.stream().mapToDouble(WholesalePriceUnit::getPrice).min().getAsDouble();
    }

    public Double getMaxPrice() {
        return priceUnits.stream().mapToDouble(WholesalePriceUnit::getPrice).max().getAsDouble();
    }

    public Double getPriceForCount(Integer count) {
        double price = 0.0;
        for (WholesalePriceUnit p : priceUnits) {
            if (count >= p.getMin() && (count <= p.getMax() || p.getMax() == null)) {
                price = p.getPrice() * count;
            }
        }
        return price;
    }

    public Double getPriceForUnitByCount(Integer count) {
        double price = 0.0;
        for (WholesalePriceUnit p : priceUnits) {
            if (count >= p.getMin() && (count <= p.getMax() || p.getMax() == null)) {
                price = p.getPrice();
            }
        }
        return price;
    }
}
