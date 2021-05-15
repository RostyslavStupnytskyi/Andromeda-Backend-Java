package rostyk.stupnytskiy.andromeda.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.country.DeliveryType;

@Getter
@Setter
public class DeliveryTypeResponse {

    private Long id;

    private String title;

    private Boolean isInternational;
    private Boolean cashOnDelivery;

    private String countryCode;

    public DeliveryTypeResponse(DeliveryType deliveryType) {
        this.id = deliveryType.getId();
        this.title = deliveryType.getTitle();
        this.isInternational = deliveryType.getInternational();
        this.countryCode = deliveryType.getCountry().getCountryCode();
        this.cashOnDelivery = deliveryType.getCashOnDelivery();
    }
}
