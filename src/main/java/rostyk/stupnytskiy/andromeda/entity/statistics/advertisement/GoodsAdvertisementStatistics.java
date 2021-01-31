package rostyk.stupnytskiy.andromeda.entity.statistics.advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder

@Entity
public class GoodsAdvertisementStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "statistics",fetch = FetchType.LAZY)
    private GoodsAdvertisement goodsAdvertisement;

    @OneToMany(mappedBy = "statistics",fetch = FetchType.LAZY)
    private List<GoodsAdvertisementMonthStatistics> monthStatistics = new ArrayList<>();

    private Double rating;

    private LocalDateTime creationDate;

    public GoodsAdvertisementStatistics() {
        this.creationDate = LocalDateTime.now();
    }


    public Long getSoldSum() {
        return monthStatistics.stream().mapToLong(GoodsAdvertisementMonthStatistics::getSold).sum();
    }

    public Long getFeedbacksSum() {
        return monthStatistics.stream().mapToLong(GoodsAdvertisementMonthStatistics::getFeedbacks).sum();
    }

    public Long getViewsSum() {
        return monthStatistics.stream().mapToLong(GoodsAdvertisementMonthStatistics::getViews).sum();
    }

    public Long getInLikeListSum() {
        return monthStatistics.stream().mapToLong(GoodsAdvertisementMonthStatistics::getInLikeList).sum();
    }

    public Long getOrdersSum() {
        return monthStatistics.stream().mapToLong(GoodsAdvertisementMonthStatistics::getOrders).sum();
    }

}

