package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.SellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.country.SellerCountriesRequest;
import rostyk.stupnytskiy.andromeda.dto.request.delivery.SellerDeliveryRequest;
import rostyk.stupnytskiy.andromeda.service.SellerService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PutMapping("/update")
    public void updateSellerData(@Valid @RequestBody SellerDataRequest request){
        sellerService.updateData(request);
    }

    @PutMapping("/countries")
    private void updaterSellerCountries(@Valid @RequestBody SellerCountriesRequest request){
        sellerService.updateSellerCountryCodes(request);
    }

    @PutMapping("/deliveries")
    private void updaterSellerDeliveries(@Valid @RequestBody SellerDeliveryRequest request){
        sellerService.updateSellerDeliveryCodes(request);
    }
}