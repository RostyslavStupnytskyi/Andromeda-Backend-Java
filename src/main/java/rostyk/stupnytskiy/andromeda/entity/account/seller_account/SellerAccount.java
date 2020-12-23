package rostyk.stupnytskiy.andromeda.entity.account.seller_account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@DiscriminatorValue("goods_seller")
public class SellerAccount extends Account {

    private String taxpayerNumber;

    private String shopName;
}
