package rostyk.stupnytskiy.andromeda.dto.request.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.WholesalePrice;

import javax.persistence.ManyToOne;

@Getter
@Setter
public class WholesalePriceUnitRequest {

    private Integer min;
    private Integer max;
    private Double price;

}
