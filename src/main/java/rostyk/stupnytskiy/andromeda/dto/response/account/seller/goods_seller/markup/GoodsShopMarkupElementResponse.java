package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElementType;

@Getter
@Setter
public class GoodsShopMarkupElementResponse {
    private Long id;

    private GoodsShopMarkupElementType elementType;

    private Integer width;

    private Integer startPosition;
    private Integer endPosition;

    private Long sellerId;

    public GoodsShopMarkupElementResponse(GoodsShopMarkupElement element) {
        this.id = element.getId();
        this.elementType = element.getElementType();
        this.width = element.getWidth();
        this.startPosition = element.getStartPosition();
        this.endPosition = element.getEndPosition();
        this.sellerId = element.getLine().getMarkup().getGoodsSeller().getId();

    }
}
