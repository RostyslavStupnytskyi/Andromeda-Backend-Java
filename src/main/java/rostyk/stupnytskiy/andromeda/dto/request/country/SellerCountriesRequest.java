package rostyk.stupnytskiy.andromeda.dto.request.country;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellerCountriesRequest {

    private List<Long> countriesId;

    private List<String> countryCodes;
}
