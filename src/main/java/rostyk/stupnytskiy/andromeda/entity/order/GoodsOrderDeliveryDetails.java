package rostyk.stupnytskiy.andromeda.entity.order;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.country.DeliveryType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsOrderDeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Country country;

    @ManyToOne
    private DeliveryType deliveryType;

    @OneToOne(mappedBy = "deliveryDetails", fetch = FetchType.LAZY)
    private GoodsOrder goodsOrder;

    private String recipient;

    private String phoneNumber;

    private String region;

    private String city;

    private String street;

    private String trackingNumber;

    private String shipment;

    private String house;

    @Lob
    private String sellerMessage;


}
