package rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerMonthStatistics;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoodsSellerStatisticsResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime registrationDate;
    private Integer advertisements;
    private Integer orders;
    private Integer sellerFeedbacks;
    private Integer orderFeedbacks;

    private Double serviceRating;
    private Double communicationRating;
    private Double orderRating;

//    private Integer

    public GoodsSellerStatisticsResponse(GoodsSellerAccount seller) {
        this.sellerFeedbacks = seller.getFeedbacks().size();
        this.orders = seller.getGoodsOrders().size();
        this.advertisements = seller.getAdvertisements().size();
        this.orderFeedbacks = seller.getAdvertisements().stream().mapToInt(a -> a.getFeedbacks().size()).sum();
        this.registrationDate = seller.getStatistics().getRegistrationDate();

        this.serviceRating =
                Math.round(seller.getStatistics().getMonthStatistics()
                        .stream()
                        .filter((s) -> s.getAverageServiceRating() != null)
                        .mapToDouble(GoodsSellerMonthStatistics::getAverageServiceRating)
                        .sum() / seller.getStatistics().getMonthStatistics().size() * 100.0) / 100.0;

        this.communicationRating =
                Math.round(seller.getStatistics().getMonthStatistics()
                        .stream()
                        .filter((s) -> s.getAverageCommunicationRating() != null)
                        .mapToDouble(GoodsSellerMonthStatistics::getAverageCommunicationRating)
                        .sum() / seller.getStatistics().getMonthStatistics().size() * 100.0) / 100.0;

        this.orderRating =
                Math.round(seller.getStatistics().getMonthStatistics()
                        .stream()
                        .filter((s) -> s.getAverageOrderRating() != null)
                        .mapToDouble(GoodsSellerMonthStatistics::getAverageOrderRating)
                        .sum() / seller.getStatistics().getMonthStatistics().size() * 100.0) / 100.0;
    }

//            statistics.setAverageOrderRating(
//                    Math.round(advertisementFeedbacks
//                    .stream()
//                    .mapToDouble(GoodsAdvertisementFeedback::getRating).sum() / advertisementFeedbacks.size() * 100.0) / 100.0
//            );
}
