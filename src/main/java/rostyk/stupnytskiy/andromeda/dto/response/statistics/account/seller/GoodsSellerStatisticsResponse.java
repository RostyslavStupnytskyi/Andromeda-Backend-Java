package rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

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

    public GoodsSellerStatisticsResponse() {

    }

//            statistics.setAverageOrderRating(
//                    Math.round(advertisementFeedbacks
//                    .stream()
//                    .mapToDouble(GoodsAdvertisementFeedback::getRating).sum() / advertisementFeedbacks.size() * 100.0) / 100.0
//            );
}
