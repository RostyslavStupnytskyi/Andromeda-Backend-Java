package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.country.CountryResponse;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;


@Getter
@Setter
public class UserDeliveryAddressResponse {

    private Long id;
    private CountryResponse country;
    private String recipient;
    private String phoneNumber;
    private String region;
    private String city;
    private String street;
    private String house;

    public UserDeliveryAddressResponse(UserDeliveryAddress address) {
        this.id = address.getId();
        this.country = new CountryResponse(address.getCountry());
        this.recipient = address.getRecipient();
        this.phoneNumber = address.getPhoneNumber();
        this.region = address.getRegion();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.house = address.getHouse();
    }
}
