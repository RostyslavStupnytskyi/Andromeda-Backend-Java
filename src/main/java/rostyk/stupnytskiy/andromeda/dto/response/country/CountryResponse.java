package rostyk.stupnytskiy.andromeda.dto.response.country;

import lombok.Getter;
import lombok.Setter;

import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.country.Region;

import javax.persistence.*;


@Getter
@Setter
public class CountryResponse {

    private Long id;
    private String countryCode;
    private Long apiId;
    private Region region;
    private String englishName;

    public CountryResponse(Country country){
        this.id = country.getId();
        this.countryCode = country.getCountryCode();
        this.apiId = country.getApiId();
        this.region = country.getRegion();
        this.englishName = country.getEnglishName();
    }
}
