package rostyk.stupnytskiy.andromeda.dto.response.statistics.account.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserStatisticsResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime registrationDate;

    private Long orders;

    private Long sellerFeedbacks;

    private Long orderFeedbacks;

    public UserStatisticsResponse() {
    }
}
