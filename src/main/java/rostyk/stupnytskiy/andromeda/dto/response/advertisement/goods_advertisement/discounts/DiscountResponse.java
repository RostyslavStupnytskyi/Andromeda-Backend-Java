package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.discounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.DiscountType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class DiscountResponse {

    private Long id;
    private DiscountType discountType;
    private Double discountValue;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime endDate;

    private Long goodsAdvertisementId;

    private Long valuesPriceCountId;

    private Boolean isCurrent;

    public DiscountResponse (Discount discount) {
        this.id = discount.getId();
        this.discountType = discount.getDiscountType();
        this.discountValue = discount.getDiscountValue();
        this.startDate = discount.getStartDate();
        this.endDate = discount.getEndDate();
        this.goodsAdvertisementId = discount.getGoodsAdvertisement().getId();
        this.valuesPriceCountId = discount.getValuesPriceCount().getId();

        if (discount.getStartDate().isBefore(LocalDateTime.now()) && discount.getEndDate().isAfter(LocalDateTime.now())) {
            this.isCurrent = true;
        } else {
            this.isCurrent = false;
        }
    }
}
