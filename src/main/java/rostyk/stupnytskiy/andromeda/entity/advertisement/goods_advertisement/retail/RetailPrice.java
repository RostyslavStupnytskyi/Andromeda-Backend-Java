package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class RetailPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private Double price;

    @ManyToOne
    private RetailGoodsAdvertisement advertisement;

}
