package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class GoodsAdvertisement extends Advertisement {

    private Boolean onlySellerCountry;

    private Double priceToSort;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<Parameter> parameters;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<ParametersValuesPriceCount> valuesPriceCounts;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subcategory subcategory;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsSellerAccount seller;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<Property> properties = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<DeliveryType> deliveryTypes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GoodsAdvertisementStatistics statistics;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<GoodsAdvertisementFeedback> feedbacks;

    @ManyToMany(mappedBy = "favoriteAdvertisements", fetch = FetchType.LAZY)
    private List<UserAccount> users;

    @Override
    public String toString() {
        return "GoodsAdvertisement{" +
                "onlySellerCountry=" + onlySellerCountry +
                ", priceToSort=" + priceToSort +
                '}';
    }
}

