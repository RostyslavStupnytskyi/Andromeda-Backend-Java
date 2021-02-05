package rostyk.stupnytskiy.andromeda.dto.response.account.user;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.country.CountryResponse;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserSettings;

@Getter
@Setter
public class UserSettingsResponse {

    private String countryCode;
    private CurrencyResponse currency;
    private Boolean getSendOrdersNotifications;

    public UserSettingsResponse(UserSettings settings) {
        this.countryCode = settings.getCountry().getCountryCode();
        this.currency = new CurrencyResponse(settings.getCurrency());
        this.getSendOrdersNotifications = settings.getGetSendOrderNotifications();
    }
}
