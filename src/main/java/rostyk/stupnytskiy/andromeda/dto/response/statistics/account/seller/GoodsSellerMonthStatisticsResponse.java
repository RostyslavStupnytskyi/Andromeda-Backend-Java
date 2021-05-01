package rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller;

import lombok.Getter;
import lombok.Setter;

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


    public GoodsSellerMonthStatisticsResponse() {

    }
}
