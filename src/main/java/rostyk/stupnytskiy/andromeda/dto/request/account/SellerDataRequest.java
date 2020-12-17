package rostyk.stupnytskiy.andromeda.dto.request.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDataRequest {

    private String taxpayerNumber;

    private String shopName;

    private Long countryApiId;

    private String countryCode;
}
