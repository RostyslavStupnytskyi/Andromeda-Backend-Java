package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
@Setter
public class GoodsShopMarkupLineResponse {
    private Long id;

    private Integer order;

    private List<GoodsShopMarkupElementResponse> elements;

    public GoodsShopMarkupLineResponse(GoodsShopMarkupLine line) {
        this.id = line.getId();
        this.order = line.getOrder();
        this.elements = line.getElements()
                .stream()
                .sorted(Comparator.comparing(GoodsShopMarkupElement::getStartPosition))
                .map(GoodsShopMarkupElementResponse::new)
                .collect(Collectors.toList());
    }
}
