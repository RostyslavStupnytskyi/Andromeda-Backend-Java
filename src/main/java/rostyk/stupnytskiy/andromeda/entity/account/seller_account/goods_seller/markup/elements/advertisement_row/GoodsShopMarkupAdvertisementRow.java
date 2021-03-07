package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsShopMarkupAdvertisementRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private GoodsShopMarkupAdvertisementRowType rowType;

    @OneToOne
    private GoodsShopMarkupElement element;

}
