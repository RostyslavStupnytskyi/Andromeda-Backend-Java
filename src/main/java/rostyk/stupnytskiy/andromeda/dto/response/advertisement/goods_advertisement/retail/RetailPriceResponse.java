package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.retail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailPrice;

import java.time.LocalDateTime;

@Getter
@Setter
public class RetailPriceResponse {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDateTime date;
    private Double price;

    public RetailPriceResponse(RetailPrice retailPrice) {
        this.id = retailPrice.getId();
        this.price = retailPrice.getPrice();
        this.date = retailPrice.getDateTime();
    }
}
