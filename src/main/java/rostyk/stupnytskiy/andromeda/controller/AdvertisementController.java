package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementCreationRequest;
import rostyk.stupnytskiy.andromeda.service.AdvertisementService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    private void save(@Valid @RequestBody AdvertisementCreationRequest request){
        System.out.println(request);
    }
}
