package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsSellerAccount extends SellerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean onlySellerCountryDelivery;

    @OneToMany(mappedBy = "seller")
    private List<GoodsAdvertisement> advertisements;

    @ManyToMany
    private Set<Country> countriesOfDelivery;

    @ManyToMany
    private Set<DeliveryType> deliveryTypes;
}
