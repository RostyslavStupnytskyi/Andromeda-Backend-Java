package rostyk.stupnytskiy.andromeda.dto.response.statistics.advertisement;

import lombok.Getter;
import lombok.Setter;

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


    public GoodsAdvertisementMonthStatisticsResponse() {

    }
}
