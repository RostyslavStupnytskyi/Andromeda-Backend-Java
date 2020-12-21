package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementChangePriceRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementCreationRequest;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;
import rostyk.stupnytskiy.andromeda.service.AdvertisementService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    private void create(@Valid @RequestBody AdvertisementCreationRequest request){
        advertisementService.createAdvertisement(request);
    }

    @PutMapping("/change-price")
    private void changeAdvertisementPrice(@Valid @RequestBody AdvertisementChangePriceRequest request, Long id){
        advertisementService.changeAdvertisementPrice(request, id);
    }

    @GetMapping
    private AdvertisementResponse test(Long id){
        return new AdvertisementResponse(advertisementService.findById(id));
    }
}
