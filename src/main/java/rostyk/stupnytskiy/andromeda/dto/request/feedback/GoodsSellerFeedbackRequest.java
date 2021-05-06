package rostyk.stupnytskiy.andromeda.dto.request.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
