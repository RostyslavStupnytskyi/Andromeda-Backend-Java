package rostyk.stupnytskiy.andromeda.dto.request.delivery;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.ManyToOne;

@Getter
@Setter
public class DeliveryTypeRequest {

    private String title;
    private Boolean international;
    private Long countryId;
    private String countryCode;
}
