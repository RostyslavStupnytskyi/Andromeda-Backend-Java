package rostyk.stupnytskiy.andromeda.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.UserDeliveryAddressRequest;
import rostyk.stupnytskiy.andromeda.service.UserDeliveryAddressService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/user-delivery-address")
public class UserDeliveryAddressController {

    @Autowired
    private UserDeliveryAddressService addressService;

    @PostMapping
    private void saveAddressToAccount(@Valid @RequestBody UserDeliveryAddressRequest request){
        addressService.saveAddressToUser(request);
    }
}
