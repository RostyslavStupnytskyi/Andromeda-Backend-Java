package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DiscountType discountType;

    private Double discountValue;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean forAllParameters;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    @ManyToOne
    private ParametersValuesPriceCount valuesPriceCount;
}
