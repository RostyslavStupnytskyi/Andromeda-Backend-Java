package rostyk.stupnytskiy.andromeda.entity.statistics.advertisement;

import lombok.*;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;

@Setter
@Getter
@AllArgsConstructor
@Builder

@Entity
public class GoodsAdvertisementMonthStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsAdvertisementStatistics statistics;

    private Month month;

    private Integer year;

    private Long views;

    private Long sold;

    private Long orders;

    private Long feedbacks;

    private Long inLikeList;

    public GoodsAdvertisementMonthStatistics() {
        this.month = LocalDateTime.now().getMonth();
        this.year = LocalDateTime.now().getYear();
        views = 0L;
        sold = 0L;
        orders = 0L;
        feedbacks = 0L;
        inLikeList = 0L;
    }
}
