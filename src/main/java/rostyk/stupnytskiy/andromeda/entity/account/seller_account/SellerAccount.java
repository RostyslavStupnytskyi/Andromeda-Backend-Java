package rostyk.stupnytskiy.andromeda.entity.account.seller_account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.SellerResponse;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor

@Entity
@DiscriminatorValue("goods_seller")
public class SellerAccount extends Account {

    private String taxpayerNumber;

    private String shopName;

    public SellerAccount() {
        super.setUserRole(UserRole.ROLE_SELLER);
    }


    public SellerResponse mapToResponse() {
        return new SellerResponse(this);
    }
}
