package rostyk.stupnytskiy.andromeda.dto.request.account.seller_account;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.account.AccountDataRequest;

@Getter
@Setter
public class SellerDataRequest {

    private String taxpayerNumber;

    private String shopName;

}
