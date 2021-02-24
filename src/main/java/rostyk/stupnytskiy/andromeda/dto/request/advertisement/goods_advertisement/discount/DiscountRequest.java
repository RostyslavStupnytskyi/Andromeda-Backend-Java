package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.discount;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.DiscountType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class DiscountRequest {

    private DiscountType discountType;

    private Double discountValue;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endDate;

    private Boolean forAllParameters;

    private Long goodsAdvertisementId;

    private Long valuesPriceCountId;
}
