package rostyk.stupnytskiy.andromeda.dto.request.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerRegistrationRequest {
    private AccountRegistrationRequest registrationRequest;
    private String shopName;
    private String taxpayerNumber;
}
