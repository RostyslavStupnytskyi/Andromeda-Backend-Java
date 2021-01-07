package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
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

    private Integer count;

    @ManyToOne
    private Subcategory subcategory;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToOne
    private GoodsSellerAccount seller;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Property> properties = new ArrayList<>();

    @ManyToMany()
    private List<DeliveryType> deliveryTypes = new ArrayList<>();

    @ManyToOne
    private Currency currency;

    @OneToOne(cascade = CascadeType.ALL)
    private GoodsAdvertisementStatistics statistics;

    @Override
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return new GoodsAdvertisementResponse(this);
    }

    public <T extends GoodsAdvertisementForSearchResponse> GoodsAdvertisementForSearchResponse mapToSearchResponse(){
        return new GoodsAdvertisementForSearchResponse(this);
    }


    @Override
    public String toString() {
        return "GoodsAdvertisement{" +
                "onlySellerCountry=" + onlySellerCountry +
                ", images=" + images +
                '}';
    }

    public GoodsAdvertisement(GoodsAdvertisement advertisement){
        setTitle(advertisement.getTitle());
        setDescription(advertisement.getDescription());
        setMainImage(advertisement.getMainImage());
        this.count = advertisement.getCount();
        this.onlySellerCountry = advertisement.getOnlySellerCountry();
        this.subcategory = advertisement.getSubcategory();
        this.images = advertisement.getImages();
        this.seller = advertisement.getSeller();
        this.deliveryTypes = advertisement.getDeliveryTypes();
        this.currency = advertisement.getCurrency();
        this.statistics = advertisement.getStatistics();
    }
}

