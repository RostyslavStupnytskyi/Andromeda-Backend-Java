package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GoodsShopMarkupResponse {
    private Long id;

    private List<GoodsShopMarkupLineResponse> lines = new ArrayList<>();

    public GoodsShopMarkupResponse(GoodsShopMarkup markup) {
        this.id = markup.getId();
        this.lines = markup.getLines().stream()
                .sorted(Comparator.comparing(GoodsShopMarkupLine::getOrder))
                .map(GoodsShopMarkupLineResponse::new)
                .collect(Collectors.toList());
    }
}
