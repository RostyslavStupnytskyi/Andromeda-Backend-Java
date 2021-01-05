package rostyk.stupnytskiy.andromeda.dto.response.account.seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.account.AccountResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;

@Getter
@Setter
public class SellerResponse extends AccountResponse {

    private String taxpayerNumber;

    private String shopName;

    public SellerResponse(SellerAccount sellerAccount){
        super(sellerAccount);
        this.taxpayerNumber = sellerAccount.getTaxpayerNumber();
        this.shopName = sellerAccount.getShopName();
    }
}
