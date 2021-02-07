package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.country.CountryRequest;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.country.Region;
import rostyk.stupnytskiy.andromeda.repository.country.CountryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country findCountryByApiId(Long id){
        return countryRepository.findByApiId(id).orElseThrow(() -> new IllegalArgumentException("Country with API Id " + id + " doesn't exist in database"));
    }

    public Country findCountryById(Long id){
        return countryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Country with Id " + id + " doesn't exist in database"));
    }

    public Country findCountryByCountryCode(String code){
        return countryRepository.findByCountryCode(code).orElseThrow(() -> new IllegalArgumentException("Country with API code " + code + " doesn't exist in database"));
    }

    public void save(CountryRequest request){
        countryRepository.save(countryRequestToCountry(request,null));
    }

    private Country countryRequestToCountry(CountryRequest request, Country country){
        if (country == null) country = new Country();
        country.setApiId(request.getApiId());
        country.setCountryCode(request.getCountryCode());
        country.setEnglishName(request.getEnglishName());
        Region region = null;
        for (Region value : Region.values()) {
            if (value.toString().equals(request.getRegion().toUpperCase())) region = value;
        }
        country.setRegion(region);
        return country;
    }

    public Set<Country> getCountriesSetByIds(List<Long> ids){
        Set<Country> countries = new HashSet<>();
        ids.forEach((id) -> countries.add(findCountryById(id)));
        return countries;
    }

    public Set<Country> getCountriesSetByCodes(List<String> codes){
        Set<Country> countries = new HashSet<>();
        codes.forEach((code) -> countries.add(findCountryByCountryCode(code)));
        return countries;
    }
}
