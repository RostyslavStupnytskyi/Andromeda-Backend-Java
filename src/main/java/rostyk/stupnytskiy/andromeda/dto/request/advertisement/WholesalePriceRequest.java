package rostyk.stupnytskiy.andromeda.dto.request.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.WholesalePriceUnit;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WholesalePriceRequest {

    private List<WholesalePriceUnitRequest> priceUnits;
}
