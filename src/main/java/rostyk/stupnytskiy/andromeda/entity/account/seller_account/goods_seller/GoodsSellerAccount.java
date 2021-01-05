package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.GoodsSellerResponse;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsSellerAccount extends SellerAccount {

    private Boolean onlySellerCountryDelivery = false;

    @OneToMany(mappedBy = "seller")
    private List<GoodsAdvertisement> advertisements;

    @ManyToMany
    private Set<DeliveryType> deliveryTypes;


    @OneToMany(mappedBy = "seller")
    private List<GoodsOrder> goodsOrders;

    public GoodsSellerResponse mapToResponse(){
        return new GoodsSellerResponse(this);
    }
}
