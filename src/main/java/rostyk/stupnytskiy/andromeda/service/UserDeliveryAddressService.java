package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.UserDeliveryAddressRequest;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.repository.AddressRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

@Service
public class UserDeliveryAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserAccountService userAccountService;

    public UserDeliveryAddress cloneAddressForOrder(UserDeliveryAddress address) {
        address.setId(null);
        address.setUser(null);
        return addressRepository.save(address);
    }

    public UserDeliveryAddress findById(Long id) {
        return addressRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void saveAddressToUser(UserDeliveryAddressRequest request) {
        save(addressRequestToAddress(request, null, userAccountService.findBySecurityContextHolder()));
    }

    public void save(UserDeliveryAddress address){
        addressRepository.save(address);
    }

    public UserDeliveryAddress addressRequestToAddress(UserDeliveryAddressRequest request, UserDeliveryAddress address, UserAccount user) {
        if (address == null) address = new UserDeliveryAddress();
        if (user != null) address.setUser(user);
        address.setRegion(request.getRegion());
        address.setCity(request.getCity());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setRecipient(request.getRecipient());
        address.setStreet(request.getStreet());
        return address;
    }
}
