package rostyk.stupnytskiy.andromeda.entity.order;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

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

    private String recipient;

    private String phoneNumber;

    private String region;

    private String city;

    private String street;

    private String trackingNumber;

    private String shipment;

    @Lob
    private String sellerMessage;

    @OneToOne(mappedBy = "deliveryDetails")
    private GoodsOrder goodsOrder;
}
