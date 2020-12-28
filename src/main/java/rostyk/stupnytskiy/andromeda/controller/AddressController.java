package rostyk.stupnytskiy.andromeda.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rostyk.stupnytskiy.andromeda.service.UserDeliveryAddressService;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private UserDeliveryAddressService addressService;

    @PostMapping
    private void saveAddressToAccount(){

    }
}
