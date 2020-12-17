package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.country.CountryRequest;
import rostyk.stupnytskiy.andromeda.service.CountryService;

@CrossOrigin
@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping
    private void save(@RequestBody CountryRequest request){
        countryService.save(request);
    }
}
