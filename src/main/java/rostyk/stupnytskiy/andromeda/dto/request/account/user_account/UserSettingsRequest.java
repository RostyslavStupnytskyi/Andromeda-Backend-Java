package rostyk.stupnytskiy.andromeda.dto.request.account.user_account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

import javax.persistence.ManyToOne;

@Getter
@Setter
public class UserSettingsRequest {

    @JsonProperty("country")
    private String countryCode;

    @JsonProperty("currency")
    private String currencyCode;
}
