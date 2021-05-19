package rostyk.stupnytskiy.andromeda.entity.account.goods_seller;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.country.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.categories.GoodsSellerAdvertisementCategory;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
//@DiscriminatorValue("goods_seller")
public class GoodsSellerAccount extends Account {

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<GoodsAdvertisement> advertisements;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<DeliveryType> deliveryTypes;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<GoodsSellerFeedback> feedbacks;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<GoodsOrder> goodsOrders;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private GoodsSellerSettings settings;

    @OneToMany(mappedBy = "goodsSeller")
    private List<GoodsSellerAdvertisementCategory> categories;

    private String shopName;

    @Lob
    private String description;


    @Override
    public String getUserName() {
        return this.shopName;
    }

    @Override
    public String toString() {
        return "GoodsSellerAccount{" +
                "Name:" + this.getShopName() +
                ", id:" + this.getId() +
                '}';
    }

}
