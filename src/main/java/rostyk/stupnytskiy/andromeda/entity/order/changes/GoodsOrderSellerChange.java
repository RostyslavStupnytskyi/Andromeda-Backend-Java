package rostyk.stupnytskiy.andromeda.entity.order.changes;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsOrderSellerChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GoodsOrder order;

    private GoodsOrderSellerChangeType type;

    private String valueFrom;

    private String valueTo;
}
