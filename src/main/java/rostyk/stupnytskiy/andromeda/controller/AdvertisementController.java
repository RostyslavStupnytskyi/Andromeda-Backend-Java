package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.response.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.service.AdvertisementService;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping()
    public void save(@RequestBody AdvertisementRequest request) throws IOException {
        advertisementService.save(request);
    }

    @PutMapping()
    public void update(Long id, @RequestBody AdvertisementRequest request) throws IOException {
        advertisementService.update(request,id);
    }

    @GetMapping
    public AdvertisementResponse findOne(Long id){
        return new AdvertisementResponse(advertisementService.findById(id));
    }

    @DeleteMapping
    public void delete(Long id){
        advertisementService.delete(id);
    }
}
