package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.Account;

@Getter
@Setter
public class AccountResponse {
    private String username;

    public AccountResponse(Account account) {
        this.username = account.getUsername();
    }
}
