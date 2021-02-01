package rostyk.stupnytskiy.andromeda.dto.response.statistics.adviertisement_views;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class UserAdvertisementViewResponse {

    private GoodsAdvertisementResponse advertisement;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;

    public UserAdvertisementViewResponse(UserAdvertisementView view) {
        this.advertisement = new GoodsAdvertisementResponse(view.getGoodsAdvertisement());
        this.date = view.getDate();
        this.time = view.getTime();
    }
}
