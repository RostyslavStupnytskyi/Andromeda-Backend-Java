package rostyk.stupnytskiy.andromeda.dto.response.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodsAdvertisementFeedbackResponse {

    private Long id;

    private Long advertisementId;

    private Double rating;

    private String text;

    private String username;
    private Long userId;

    private List<String> images;

    private String countryCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationDate;

    public GoodsAdvertisementFeedbackResponse(GoodsAdvertisementFeedback feedback) {
        this.id = feedback.getId();
        this.advertisementId = feedback.getGoodsAdvertisement().getId();
        this.rating = feedback.getRating();
        this.text = feedback.getText();
        this.userId = feedback.getUserAccount().getId();
        this.username = feedback.getUserAccount().getUserName();
        this.images = feedback.getImages();
        this.countryCode = feedback.getCountry().getCountryCode();
        this.creationDate = feedback.getCreationDate();
    }
}
