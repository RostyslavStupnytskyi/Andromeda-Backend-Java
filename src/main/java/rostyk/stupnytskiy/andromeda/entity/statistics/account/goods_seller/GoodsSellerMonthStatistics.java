package rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;

@Getter
@Setter
@AllArgsConstructor
@Builder

@Entity
public class GoodsSellerMonthStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Month month;

    private Integer year;

    private Long advertisementViews;

    private Long profileViews;

    private Double averageCommunicationRating;

    private Double averageServiceRating;

    private Double averageOrderRating;

    private Long monthOrders;

    private Long sellerFeedbacks;

    private Long orderFeedbacks;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne()
    @JsonIgnore
    private GoodsSellerStatistics sellerStatistics;

    public GoodsSellerMonthStatistics() {
        this.month = LocalDateTime.now().getMonth();
        this.year = LocalDateTime.now().getYear();
        this.advertisementViews = 0L;
        this.profileViews = 0L;
        this.monthOrders = 0L;
        this.sellerFeedbacks = 0L;
        this.orderFeedbacks = 0L;

    }
}
