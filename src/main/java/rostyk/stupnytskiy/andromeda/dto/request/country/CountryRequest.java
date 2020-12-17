package rostyk.stupnytskiy.andromeda.dto.request.country;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.country.Region;

@Getter
@Setter
public class CountryRequest {

    private String countryCode;

    private Long apiId;

    private String region;

    private String englishName;
}
