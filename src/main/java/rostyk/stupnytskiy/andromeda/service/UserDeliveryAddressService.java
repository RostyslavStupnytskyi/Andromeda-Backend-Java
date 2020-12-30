package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.UserDeliveryAddressRequest;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.repository.AddressRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

@Service
public class UserDeliveryAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CountryService countryService;

    public UserDeliveryAddress findById(Long id) {
        return addressRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void saveAddressToUser(UserDeliveryAddressRequest request) {
        save(addressRequestToAddress(request, null));
    }

    public void save(UserDeliveryAddress address){
        addressRepository.save(address);
    }

    public UserDeliveryAddress addressRequestToAddress(UserDeliveryAddressRequest request, UserDeliveryAddress address) {
        if (address == null) address = new UserDeliveryAddress();
        address.setRegion(request.getRegion());
        address.setCity(request.getCity());
        address.setUser(userAccountService.findBySecurityContextHolder());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setRecipient(request.getRecipient());
        address.setStreet(request.getStreet());
        address.setCountry(countryService.findCountryByCountryCode(request.getCountryCode()));
        return address;
    }
}
