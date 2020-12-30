package rostyk.stupnytskiy.andromeda.dto.request;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class UserDeliveryAddressRequest {

    private String recipient;

    private String phoneNumber;

    private String region;

    private String city;

    private String street;

    private String countryCode;
}
