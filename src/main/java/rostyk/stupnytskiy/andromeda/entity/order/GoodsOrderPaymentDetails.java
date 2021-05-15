package rostyk.stupnytskiy.andromeda.entity.order;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsOrderPaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "paymentDetails")
    private GoodsOrder goodsOrder;

    @ManyToOne
    private Currency currency;

    private GoodsOrderPaymentMethod paymentMethod;
}
