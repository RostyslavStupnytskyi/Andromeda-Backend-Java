package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.retail;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailPrice;

import java.time.LocalDateTime;

@Getter
@Setter
public class RetailPriceResponse {

    private Long id;
    private LocalDateTime dateTime;
    private Double price;

    public RetailPriceResponse(RetailPrice retailPrice) {
        this.id = retailPrice.getId();
        this.price = retailPrice.getPrice();
        this.dateTime = retailPrice.getDateTime();
    }
}
