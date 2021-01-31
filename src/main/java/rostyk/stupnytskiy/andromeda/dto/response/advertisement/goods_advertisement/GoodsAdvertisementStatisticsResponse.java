package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoodsAdvertisementStatisticsResponse {

    private Long id;
    private Double rating;
    private Long views;

    private Long sold;
    private Long orders;
    private Long feedbacks;
    private Long likes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime creationDate;

    public GoodsAdvertisementStatisticsResponse(GoodsAdvertisementStatistics statistics) {
        this.id = statistics.getId();
        this.rating = statistics.getRating();
        this.views = statistics.getViewsSum();
        this.sold = statistics.getSoldSum();
        this.creationDate = statistics.getCreationDate();
        this.feedbacks = statistics.getFeedbacksSum();
        this.likes = statistics.getInLikeListSum();
        this.orders = statistics.getOrdersSum();
    }
}
