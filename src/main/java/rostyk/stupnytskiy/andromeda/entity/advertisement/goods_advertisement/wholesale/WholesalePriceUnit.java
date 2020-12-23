package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class WholesalePriceUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer min;

    private Integer max;

    private Double price;

    @ManyToOne
    private WholesalePrice wholesalePrice;
}
