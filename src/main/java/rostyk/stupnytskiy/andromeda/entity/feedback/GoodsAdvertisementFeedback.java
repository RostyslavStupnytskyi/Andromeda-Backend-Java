package rostyk.stupnytskiy.andromeda.entity.feedback;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsAdvertisementFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    @OneToOne
    private GoodsOrderItem goodsOrderItem;

    private Double rating;

    private String text;

    @ManyToOne
    private UserAccount userAccount;

    private LocalDateTime creationDate;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToOne
    private Country country;
}
