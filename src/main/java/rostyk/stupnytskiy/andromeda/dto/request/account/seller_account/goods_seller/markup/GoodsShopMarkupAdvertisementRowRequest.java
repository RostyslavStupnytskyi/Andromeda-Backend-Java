package rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row.GoodsShopMarkupAdvertisementRowType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
public class GoodsShopMarkupAdvertisementRowRequest {

    private GoodsShopMarkupAdvertisementRowType rowType;
    private Integer line;
    private Integer position;
}
