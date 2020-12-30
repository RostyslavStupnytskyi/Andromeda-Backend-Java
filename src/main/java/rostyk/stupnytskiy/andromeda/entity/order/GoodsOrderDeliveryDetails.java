package rostyk.stupnytskiy.andromeda.entity.order;

import lombok.*;

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

    private String recipient;

    private String phoneNumber;

    private String region;

    private String city;

    private String street;

    private String trackingNumber;

    private String shipment;

    @OneToOne(mappedBy = "deliveryDetails")
    private GoodsOrder goodsOrder;
}
