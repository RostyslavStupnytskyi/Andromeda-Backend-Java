package rostyk.stupnytskiy.andromeda.dto.response.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.country.CountryResponse;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private String avatar;
    private UserRole userRole;
    private Long countryApiId;

    public AccountResponse(Account account) {
//        this.username = account.getUsername();
        this.id = account.getId();
        this.avatar = account.getAvatar();
        this.userRole = account.getUserRole();
        this.countryApiId = account.getCountry().getApiId();
    }

}
