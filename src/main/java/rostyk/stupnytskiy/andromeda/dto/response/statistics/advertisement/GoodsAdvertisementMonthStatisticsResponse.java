package rostyk.stupnytskiy.andromeda.dto.response.statistics.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;

import java.time.Month;

@Getter
@Setter
public class GoodsAdvertisementMonthStatisticsResponse {

    private Month month;

    private Integer year;

    private Long views;

    private Long sold;

    private Long orders;

    private Long feedbacks;

    private Long inLikeList;


    public GoodsAdvertisementMonthStatisticsResponse(GoodsAdvertisementMonthStatistics statistics) {
        this.month = statistics.getMonth();
        this.year = statistics.getYear();
        this.views = statistics.getViews();
        this.sold = statistics.getSold();
        this.orders = statistics.getOrders();
        this.feedbacks = statistics.getFeedbacks();
        this.inLikeList = statistics.getInLikeList();
    }
}
