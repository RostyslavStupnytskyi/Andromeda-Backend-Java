package rostyk.stupnytskiy.andromeda.dto.response.account.user;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;

@Getter
@Setter
public class UserDataResponse {

    private String username;
    private String avatar;

    private UserSettingsResponse settings;

    public UserDataResponse(UserAccount user){
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.settings = new UserSettingsResponse(user.getSettings());
    }
}
