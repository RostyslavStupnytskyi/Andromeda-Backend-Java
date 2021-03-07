package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsShopMarkupElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private GoodsShopMarkupElementType elementType;

    private Integer width;

    private Integer startPosition;
    private Integer endPosition;

    @ManyToOne
    private GoodsShopMarkupLine line;

    public GoodsShopMarkupElement(GoodsShopMarkupElementType elementType, Integer startPosition) {
        this.elementType = elementType;
        switch (elementType) {
            case MARKUP_ADVERTISING_BANNER: setWithAndPosition(2, startPosition); break;
            case MARKUP_ADVERTISEMENT_VIEW: setWithAndPosition(1, startPosition); break;
            case MARKUP_ADVERTISEMENTS_ROW: setWithAndPosition(4, startPosition); break;
        }
    }

    private void setWithAndPosition(Integer width, Integer startPosition) {
        this.width = width;
        this.startPosition = startPosition;
        this.endPosition = this.startPosition + this.width - 1;
    }

    public boolean isAtPosition(Integer position) {
        return (position >= this.startPosition) && (position <= this.endPosition);
    }
}
