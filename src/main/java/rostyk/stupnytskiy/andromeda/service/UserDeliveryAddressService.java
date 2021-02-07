package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.UserDeliveryAddressRequest;
import rostyk.stupnytskiy.andromeda.dto.response.UserDeliveryAddressResponse;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.repository.country.AddressRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

import java.util.List;
import java.util.stream.Collectors;

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

    public UserDeliveryAddress saveAddressToUser(UserDeliveryAddressRequest request) {
        UserDeliveryAddress address = save(addressRequestToAddress(request, null));
        UserAccount user = userAccountService.findBySecurityContextHolder();
        if (user.getAddresses().size() == 1) {
            user.setDefaultAddress(address);
            userAccountService.save(user);
        }

        return address;
    }

    public UserDeliveryAddress updateUserAddress(UserDeliveryAddressRequest request, Long id) {
        return save(addressRequestToAddress(request, findByUserAndId(id)));
    }

    public UserDeliveryAddress save(UserDeliveryAddress address) {
        return addressRepository.save(address);
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
        address.setHouse(request.getHouse());
        return address;
    }

    public UserDeliveryAddressResponse getDefaultUserDeliveryAddress() {
        UserAccount userAccount = userAccountService.findBySecurityContextHolder();
        if (userAccount.getDefaultAddress() == null) return null;
        else
            return new UserDeliveryAddressResponse(userAccount.getDefaultAddress());
    }

    public List<UserDeliveryAddressResponse> getUserAddresses() {
        return addressRepository.findAllByUser(userAccountService.findBySecurityContextHolder())
                .stream()
                .map(UserDeliveryAddressResponse::new)
                .collect(Collectors.toList());
    }

    public UserDeliveryAddress findByUserAndId(Long id) {
        return addressRepository.findByUserAndId(userAccountService.findBySecurityContextHolder(), id).orElseThrow(IllegalArgumentException::new);
    }


    public void makeAddressDefault(Long id) {
        UserDeliveryAddress address = findByUserAndId(id);
        UserAccount user = userAccountService.findBySecurityContextHolder();
        user.setDefaultAddress(address);
        userAccountService.save(user);
    }


    public void deleteAddress(Long id) {
        addressRepository.delete(findByUserAndId(id));
    }
}
