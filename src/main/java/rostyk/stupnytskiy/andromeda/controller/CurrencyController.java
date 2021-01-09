package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.country.CurrencyRequest;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;

import java.util.List;

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

    @GetMapping("/all")
    private List<CurrencyResponse> getAll(){
        return currencyService.geAll();
    }

    @GetMapping
    private void test(){
        currencyService.reloadCurrencyValues();
    }

}
