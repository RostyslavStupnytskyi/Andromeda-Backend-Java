package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.country.CurrencyRequest;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;

@CrossOrigin
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping
    private void save(@RequestBody  CurrencyRequest request){
        currencyService.save(request);
    }
}
