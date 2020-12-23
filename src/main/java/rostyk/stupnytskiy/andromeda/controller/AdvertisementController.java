package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.service.AdvertisementService;

@CrossOrigin
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    private void create(){
        advertisementService.createAdvertisement();
    }


    @GetMapping
    private void get(Long id){
        advertisementService.getOne(id);
    }
//    @PostMapping
//    private void create(@Valid @RequestBody AdvertisementCreationRequest request){
//        advertisementService.createAdvertisement(request);
//    }
//
//    @PutMapping("/change-price")
//    private void changeAdvertisementPrice(@Valid @RequestBody AdvertisementChangePriceRequest request, Long id){
//        advertisementService.changeAdvertisementPrice(request, id);
//    }

//    @GetMapping
//    private AdvertisementResponse test(Long id){
//        return new AdvertisementResponse(advertisementService.findById(id));
//    }
}
