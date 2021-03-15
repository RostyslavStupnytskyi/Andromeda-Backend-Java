package rostyk.stupnytskiy.andromeda.service.account.seller.markup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.GoodsShopMarkupLineRepository;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;

@Service
public class GoodsShopMarkupLineService {

    @Autowired
    private GoodsShopMarkupLineRepository goodsShopMarkupLineRepository;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;


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

    public GoodsShopMarkupLine save(GoodsShopMarkupLine line) {
        return goodsShopMarkupLineRepository.save(line);
    }

    public GoodsShopMarkupLine findById(Long id) {
        return goodsShopMarkupLineRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void delete(GoodsShopMarkupLine line) {
        goodsShopMarkupLineRepository.delete(line);
    }

    public void lineUp(Long lineId) {
        GoodsShopMarkupLine lineToUp = findById(lineId);
        if (lineToUp.getOrder() > 1) {
            lineToUp.setOrder(lineToUp.getOrder() - 1);
            GoodsShopMarkupLine lineDown = getMarkupLineByMarkupAndLine(goodsSellerAccountService.findBySecurityContextHolder().getMarkup(), lineToUp.getOrder());
            lineDown.setOrder(lineToUp.getOrder() + 1);

            goodsShopMarkupLineRepository.save(lineToUp);
            goodsShopMarkupLineRepository.save(lineDown);
        }
    }

    public void lineDown(Long lineId) {
        GoodsShopMarkupLine lineToDown = findById(lineId);
        GoodsShopMarkup goodsShopMarkup = goodsSellerAccountService.findBySecurityContextHolder().getMarkup();
        if (lineToDown.getOrder() != goodsShopMarkup.getLines().size()) {
            lineToDown.setOrder(lineToDown.getOrder() + 1);
            GoodsShopMarkupLine lineUp = getMarkupLineByMarkupAndLine(goodsShopMarkup, lineToDown.getOrder());
            lineUp.setOrder(lineToDown.getOrder() - 1);

            goodsShopMarkupLineRepository.save(lineToDown);
            goodsShopMarkupLineRepository.save(lineUp);
        }
    }
}
