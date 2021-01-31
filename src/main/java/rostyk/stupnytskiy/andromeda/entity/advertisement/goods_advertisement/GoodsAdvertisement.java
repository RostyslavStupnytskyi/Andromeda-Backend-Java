package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.AdvertisementEntity;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class GoodsAdvertisement extends Advertisement implements AdvertisementEntity, GoodsAdvertisementEntity {

    private Boolean onlySellerCountry;

    private Integer count;

    private Double priceToSort;

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
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return new GoodsAdvertisementResponse(this);
    }

    @Override
    public <T extends GoodsAdvertisementForSearchResponse> GoodsAdvertisementForSearchResponse mapToSearchResponse() {
        return new GoodsAdvertisementForSearchResponse(this);
    }

    public Double getPriceForSort() {
        return 0.0;
    }

    @Override
    public String toString() {
        return "GoodsAdvertisement{" +
                "onlySellerCountry=" + onlySellerCountry +
                ", images=" + images +
                '}';
    }

    public GoodsAdvertisement(GoodsAdvertisement advertisement) {
        setTitle(advertisement.getTitle());
        setDescription(advertisement.getDescription());
        setMainImage(advertisement.getMainImage());
        this.count = advertisement.getCount();
        this.onlySellerCountry = advertisement.getOnlySellerCountry();
        this.subcategory = advertisement.getSubcategory();
        this.images = advertisement.getImages();
        this.seller = advertisement.getSeller();
        this.deliveryTypes = advertisement.getDeliveryTypes();
        this.statistics = advertisement.getStatistics();
    }

    public Double getPriceByDateAndForCount(LocalDateTime date, Integer count) {
        return 0.0;
    }

    public Double getPriceByDateAndForUnitCount(LocalDateTime date, Integer count) {
        return 0.0;
    }


    public Double getPriceByCount(Integer count) {
        return 0.0;
    }

    public Double getPriceForCart(Integer count) {
        return 0.0;
    }
}

