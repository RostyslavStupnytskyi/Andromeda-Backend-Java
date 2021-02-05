package rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerMonthStatistics;

import java.time.Month;

@Getter
@Setter
public class GoodsSellerMonthStatisticsResponse {

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


    public GoodsSellerMonthStatisticsResponse(GoodsSellerMonthStatistics statistics) {
        this.month = statistics.getMonth();
        this.year = statistics.getYear();
        this.advertisementViews = statistics.getAdvertisementViews();
        this.profileViews = statistics.getProfileViews();
        this.averageCommunicationRating = statistics.getAverageCommunicationRating();
        this.averageServiceRating = statistics.getAverageServiceRating();
        this.averageOrderRating = statistics.getAverageOrderRating();
        this.monthOrders = statistics.getMonthOrders();
        this.sellerFeedbacks = statistics.getSellerFeedbacks();
        this.orderFeedbacks = statistics.getOrderFeedbacks();
    }
}
