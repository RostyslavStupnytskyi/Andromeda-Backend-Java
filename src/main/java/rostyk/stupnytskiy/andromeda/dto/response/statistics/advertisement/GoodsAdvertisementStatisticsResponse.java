package rostyk.stupnytskiy.andromeda.dto.response.statistics.advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoodsAdvertisementStatisticsResponse {

    private Double rating;
    private Long views;

    private Long sold;
    private Long orders;
    private Long feedbacks;
    private Long likes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime creationDate;

}
