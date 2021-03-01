package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.discounts;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DiscountsForParametersValuesPriceCountResponse {

    private ParametersValuesPriceCountResponse priceCountResponse;
    private List<DiscountResponse> discounts;

    public DiscountsForParametersValuesPriceCountResponse(ParametersValuesPriceCount parametersValuesPriceCount) {
        this.priceCountResponse = new ParametersValuesPriceCountResponse(parametersValuesPriceCount);
        this.discounts = parametersValuesPriceCount.getDiscounts()
                .stream()
                .filter((d) -> d.getEndDate().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Discount::getStartDate))
                .map(DiscountResponse::new)
                .collect(Collectors.toList());
    }
}
