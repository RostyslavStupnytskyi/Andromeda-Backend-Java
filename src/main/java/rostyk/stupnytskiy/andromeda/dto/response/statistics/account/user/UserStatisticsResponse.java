package rostyk.stupnytskiy.andromeda.dto.response.statistics.account.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserStatistics;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserStatisticsResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime registrationDate;

    private Long orders;

    private Long sellerFeedbacks;

    private Long orderFeedbacks;

    public UserStatisticsResponse(UserStatistics statistics) {
        this.registrationDate = statistics.getRegistrationDate();
        this.orders = statistics.getMonthStatisticsList().stream().mapToLong(UserMonthStatistics::getOrders).sum();
        this.sellerFeedbacks = statistics.getMonthStatisticsList().stream().mapToLong(UserMonthStatistics::getSellerFeedbacks).sum();
        this.orderFeedbacks = statistics.getMonthStatisticsList().stream().mapToLong(UserMonthStatistics::getOrderFeedbacks).sum();
    }
}
