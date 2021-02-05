package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller;

import lombok.*;


import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class GoodsSellerSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean sendNewOrderNotifications = true;

    private Boolean sendOrderReceivedNotifications = true;

    @OneToOne(mappedBy = "settings")
    private GoodsSellerAccount goodsSellerAccount;
}
