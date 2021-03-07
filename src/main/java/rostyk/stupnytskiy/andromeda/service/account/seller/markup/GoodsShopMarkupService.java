package rostyk.stupnytskiy.andromeda.service.account.seller.markup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.GoodsShopMarkupRepository;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;

@Service
public class GoodsShopMarkupService {

    @Autowired
    private GoodsShopMarkupRepository goodsShopMarkupRepository;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private GoodsShopMarkupLineService goodsShopMarkupLineService;

    @Autowired
    private GoodsShopMarkupElementService goodsShopMarkupElementService;

    public void createDefaultMarkup(GoodsSellerAccount seller) {
        GoodsShopMarkup goodsShopMarkup =  goodsShopMarkupRepository.save(
                GoodsShopMarkup.builder().goodsSeller(seller).build());
        createDefaultLines(goodsShopMarkup);
        goodsShopMarkupElementService.createDefaultElements(goodsShopMarkup);
    }

    private void createDefaultLines(GoodsShopMarkup markup) {
        for (int i = 1; i <= 3; i++) {
            goodsShopMarkupLineService.getMarkupLineByMarkupAndLineOrElseCreateNew(markup, i);
        }
    }


}
