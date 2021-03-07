package rostyk.stupnytskiy.andromeda.service.account.seller.markup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.GoodsShopMarkupLineRepository;

@Service
public class GoodsShopMarkupLineService {

    @Autowired
    private GoodsShopMarkupLineRepository goodsShopMarkupLineRepository;


    public GoodsShopMarkupLine getMarkupLineByMarkupAndLine(GoodsShopMarkup markup, Integer order) {
        return this.goodsShopMarkupLineRepository.findOneByMarkupAndOrder(markup, order).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsShopMarkupLine getMarkupLineByMarkupAndLineOrElseCreateNew(GoodsShopMarkup markup, Integer order) {
        try {
            return getMarkupLineByMarkupAndLine(markup, order);
        } catch (IllegalArgumentException e) {
            GoodsShopMarkupLine line = new GoodsShopMarkupLine();
            line.setMarkup(markup);
            line.setOrder(order);
            return goodsShopMarkupLineRepository.save(line);
        }
    }
}
