package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class GoodsAdvertisementStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "statistics")
    private GoodsAdvertisement goodsAdvertisement;

    private Double rating;

    private Integer views = 0;

    private Integer numberOfOrders = 0;

    private Long sold = 0L;

    private LocalDateTime creationDate;

}

