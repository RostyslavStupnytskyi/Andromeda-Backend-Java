package rostyk.stupnytskiy.andromeda.dto.response.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
