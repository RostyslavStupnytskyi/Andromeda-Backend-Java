package rostyk.stupnytskiy.andromeda.dto.request.account.user_account;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.account.AccountDataRequest;

@Getter
@Setter
public class UserDataRequest  {

    private String username;

    private String avatar;
}
