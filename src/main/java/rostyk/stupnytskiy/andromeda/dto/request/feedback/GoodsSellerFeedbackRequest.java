package rostyk.stupnytskiy.andromeda.dto.request.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;


import javax.persistence.ManyToOne;

@Getter
@Setter
public class GoodsSellerFeedbackRequest {

    @JsonProperty("communication")
    private Double communicationRating;

    @JsonProperty("service")
    private Double serviceRating;

    private Long orderId;

    private String text;
}
