package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.wholesale;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class WholesalePriceResponse {

    private Long id;

    private LocalDateTime dateTime;

    private List<WholesalePriceUnitResponse> priceUnits;

    public WholesalePriceResponse(WholesalePrice wholesalePrice) {
        this.id = wholesalePrice.getId();
        this.dateTime = wholesalePrice.getDateTime();
        this.priceUnits = wholesalePrice.getPriceUnits()
                .stream()
                .map(WholesalePriceUnitResponse::new)
                .collect(Collectors.toList());
    }
}
