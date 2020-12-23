package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.AdvertisementEntity;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@DiscriminatorValue("goods_retail")
public class RetailGoodsAdvertisement extends GoodsAdvertisement implements AdvertisementEntity {

    @OneToMany(mappedBy = "advertisement")
    private List<RetailPrice> retailPrices;

    @Override
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return null;
    }

    @Override
    public String toString() {
        return "RetailGoodsAdvertisement{"
                + '\'' +" is only seller " + super.getOnlySellerCountry() +
                '}';
    }
}
