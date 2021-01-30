package rostyk.stupnytskiy.andromeda.entity.statistics.advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

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

    private Long sold = 0L;
    private Long orders = 0L;

    private Integer feedbacks = 0;

    private Integer inLikesList = 0;

    private LocalDateTime creationDate;



}

