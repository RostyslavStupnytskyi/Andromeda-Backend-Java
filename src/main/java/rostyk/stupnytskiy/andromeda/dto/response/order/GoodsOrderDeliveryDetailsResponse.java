package rostyk.stupnytskiy.andromeda.dto.response.order;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.DeliveryTypeResponse;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderDeliveryDetails;

import javax.persistence.*;

@Getter
@Setter
public class GoodsOrderDeliveryDetailsResponse {

    private Long id;
    private String countryCode;
    private String recipient;
    private String phoneNumber;
    private String region;
    private String city;
    private String street;
    private String trackingNumber;
    private String shipment;
    private String sellerMessage;
    private String house;
    private DeliveryTypeResponse delivery;

    public GoodsOrderDeliveryDetailsResponse(GoodsOrderDeliveryDetails details){
        this.id = details.getId();
        this.countryCode = details.getCountry().getCountryCode();
        this.recipient = details.getRecipient();
        this.phoneNumber = details.getPhoneNumber();
        this.region = details.getRegion();
        this.city = details.getCity();
        this.street = details.getStreet();
        this.trackingNumber = details.getTrackingNumber();
        this.shipment = details.getShipment();
        this.sellerMessage = details.getSellerMessage();
        this.house = details.getHouse();
        this.delivery = new DeliveryTypeResponse(details.getDeliveryType());
    }
}
