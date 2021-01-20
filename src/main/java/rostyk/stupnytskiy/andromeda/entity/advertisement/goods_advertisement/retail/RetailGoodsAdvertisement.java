package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.AdvertisementEntity;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisementEntity;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@DiscriminatorValue("goods_retail")
public class RetailGoodsAdvertisement extends GoodsAdvertisement implements AdvertisementEntity, GoodsAdvertisementEntity {

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<RetailPrice> retailPrices;

    @Override
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return new RetailGoodsAdvertisementResponse(this);
    }

    public <T extends GoodsAdvertisementForSearchResponse> GoodsAdvertisementForSearchResponse mapToSearchResponse() {
        return new RetailGoodsAdvertisementForSearchResponse(this);
    }

    @Override
    public String toString() {
        return "RetailGoodsAdvertisement{"
                + '\'' + " is only seller " + super.getOnlySellerCountry() +
                '}';
    }

    @Override
    public Double getPriceForSort() {
        return getCurrentPrice().getPrice();
    }

    public RetailPrice getCurrentPrice() {
        retailPrices.sort(Comparator.comparing(RetailPrice::getDateTime));
        return retailPrices.get(retailPrices.size() - 1);
    }

    public Double getPriceByDateAndForCount(LocalDateTime date, Integer count) {
        return getPriceByDate(date).getPrice() * count;
    }

    public Double getPriceByDateAndForUnitCount(LocalDateTime date, Integer count) {
        return getPriceByDate(date).getPrice();
    }

    private RetailPrice getPriceByDate(LocalDateTime date) {
        for (int i = 0; i < retailPrices.size(); i++) {
            if (date.isAfter(retailPrices.get(i).getDateTime()) && (i == retailPrices.size() - 1))
                return retailPrices.get(i);
            if (date.isAfter(retailPrices.get(i).getDateTime())
                    && date.isBefore(retailPrices.get(i + 1).getDateTime()))
                return retailPrices.get(i);
        }
        return new RetailPrice();
    }

    public Double getPriceByCount(Integer count) {
        return getCurrentPrice().getPrice() * count;
    }

    public Double getPriceForCart(Integer count) {
        return getCurrentPrice().getPrice();
    }

    public RetailGoodsAdvertisement(GoodsAdvertisement advertisement) {
        super(advertisement);
    }
}
