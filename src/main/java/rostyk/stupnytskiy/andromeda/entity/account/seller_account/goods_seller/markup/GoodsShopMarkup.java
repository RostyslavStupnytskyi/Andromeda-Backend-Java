package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class GoodsShopMarkup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "markup")
    private List<GoodsShopMarkupLine> lines = new ArrayList<>();

    @OneToOne(mappedBy = "markup")
    private GoodsSellerAccount goodsSeller;

}
