package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "wholesalePrice", cascade = CascadeType.ALL)
    private List<WholesalePriceUnit> priceUnits;

    @ManyToOne
    private WholesaleGoodsAdvertisement advertisement;

    public String getStringPriceSides() {
        return (priceUnits.stream().mapToDouble(WholesalePriceUnit::getPrice).min().getAsDouble()
                + " - "
                + priceUnits.stream().mapToDouble(WholesalePriceUnit::getPrice).max().getAsDouble());
    }

}
