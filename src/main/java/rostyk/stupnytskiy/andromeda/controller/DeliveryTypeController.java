package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.delivery.DeliveryTypeRequest;
import rostyk.stupnytskiy.andromeda.dto.response.DeliveryTypeResponse;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/delivery")
public class DeliveryTypeController {

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @PostMapping
    private void save(@RequestBody DeliveryTypeRequest request) {
        deliveryTypeService.save(request);
    }

    @PutMapping
    private void update(@RequestBody DeliveryTypeRequest request, Long id) {
        deliveryTypeService.update(request, id);
    }

    @GetMapping("/all")
    private List<DeliveryTypeResponse> getAll() {
        return deliveryTypeService.getAll();
    }

    @GetMapping("/{code}")
    private List<DeliveryTypeResponse> getByCountry(
            @PathVariable String code
    ) {
        return deliveryTypeService.getAllByCountryCode(code);
    }

    @GetMapping("advertisement")
    private List<DeliveryTypeResponse> getAllByAdvertisement(Long id) {
        return deliveryTypeService.getAllByAdvertisementId(id);
    }

    @GetMapping("/account")
    private List<DeliveryTypeResponse> getAllByAccountCountry() {
        return deliveryTypeService.getALlByAccountCountry();
    }
}
