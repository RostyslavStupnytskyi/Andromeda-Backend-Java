package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsShopMarkupAdvertisementView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private GoodsShopMarkupElement element;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    private GoodsShopMarkupAdvertisingViewType viewType;
}
