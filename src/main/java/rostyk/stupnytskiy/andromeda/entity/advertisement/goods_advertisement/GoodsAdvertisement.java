package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.CartItem;
import rostyk.stupnytskiy.andromeda.entity.Category;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.AdvertisementEntity;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class GoodsAdvertisement extends Advertisement implements AdvertisementEntity {

    private Boolean onlySellerCountry;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Subcategory subcategory;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    private GoodsSellerAccount seller;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "advertisement")
    private List<CartItem> cartItems;

    @ManyToOne
    private Currency currency;

    @Override
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return super.mapToResponse();
    }


    @Override
    public String toString() {
        return "GoodsAdvertisement{" +
                "onlySellerCountry=" + onlySellerCountry +
                ", images=" + images +
                '}';
    }
}

