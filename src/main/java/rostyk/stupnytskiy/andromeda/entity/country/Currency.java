package rostyk.stupnytskiy.andromeda.entity.country;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Currency {

    @Id
    private String code;

    private String name;

    private String symbol;

    private Double priceInHryvnia;
}
