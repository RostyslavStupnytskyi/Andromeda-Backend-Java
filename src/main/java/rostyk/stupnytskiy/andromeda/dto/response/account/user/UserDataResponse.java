package rostyk.stupnytskiy.andromeda.dto.response.account.user;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.account.user.UserStatisticsResponse;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;

@Getter
@Setter
public class UserDataResponse {

    private Long id;
    private String username;
    private String avatar;

    private UserSettingsResponse settings;
    private UserStatisticsResponse statistics;

    public UserDataResponse(UserAccount user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.settings = new UserSettingsResponse(user.getSettings());
        this.statistics = new UserStatisticsResponse(user.getUserStatistics());
    }
}
