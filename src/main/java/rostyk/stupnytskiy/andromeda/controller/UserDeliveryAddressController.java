package rostyk.stupnytskiy.andromeda.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.UserDeliveryAddressRequest;
import rostyk.stupnytskiy.andromeda.dto.response.UserDeliveryAddressResponse;
import rostyk.stupnytskiy.andromeda.service.UserDeliveryAddressService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user-delivery-address")
public class UserDeliveryAddressController {

    @Autowired
    private UserDeliveryAddressService addressService;

    @PostMapping
    private UserDeliveryAddressResponse saveAddressToAccount(@Valid @RequestBody UserDeliveryAddressRequest request){
        return new UserDeliveryAddressResponse(addressService.saveAddressToUser(request));
    }

    @GetMapping("user")
    private List<UserDeliveryAddressResponse> getUserDeliveryAddresses(){
        return addressService.getUserAddresses();
    }

    @PutMapping("/make-default")
    private void makeAddressDefault(Long id) {
        addressService.makeAddressDefault(id);
    }

    @GetMapping("/default")
    private UserDeliveryAddressResponse getDefaultUserAddress(){
        return addressService.getDefaultUserDeliveryAddress();
    }
}
