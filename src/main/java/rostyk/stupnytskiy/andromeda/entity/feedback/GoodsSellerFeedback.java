package rostyk.stupnytskiy.andromeda.entity.feedback;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsSellerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double communicationRating;

    private Double serviceRating;

    private LocalDateTime dateTime;

    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsSellerAccount seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount user;

    @OneToOne(fetch = FetchType.LAZY)
    private GoodsOrder goodsOrder;

}
