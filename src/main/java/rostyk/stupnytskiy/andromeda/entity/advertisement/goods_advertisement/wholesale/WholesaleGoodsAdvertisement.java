package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.AdvertisementEntity;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@DiscriminatorValue("goods_wholesale")
public class WholesaleGoodsAdvertisement extends GoodsAdvertisement implements AdvertisementEntity {

    @OneToMany(mappedBy = "advertisement")
    private List<WholesalePrice> wholesalePrices;

    @Override
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return new WholesaleGoodsAdvertisementResponse(this);
    }

    @Override
    public String toString() {
        return "WholesaleGoodsAdvertisement{" +
                 + '\'' + " is only seller " + super.getOnlySellerCountry() +
//                ", wholesalePrices=" + wholesalePrices +
                '}';
    }


    public WholesalePrice getCurrentPrice(){
        wholesalePrices.sort(Comparator.comparing(WholesalePrice::getDateTime));
        return wholesalePrices.get(wholesalePrices.size() - 1);
    }
}
