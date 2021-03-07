package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisementRowRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisementViewRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisingBannerRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.GoodsShopMarkupResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisementRowResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisementViewResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisingBannerResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.markup.GoodsShopMarkupElementService;
import rostyk.stupnytskiy.andromeda.service.account.seller.markup.GoodsShopMarkupService;

@CrossOrigin
@RestController
@RequestMapping("/goods-shop-markup")
public class GoodsShopMarkupController {

    @Autowired
    private GoodsShopMarkupService goodsShopMarkupService;

    @Autowired
    private GoodsShopMarkupElementService goodsShopMarkupElementService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @PostMapping("create-banner")
    private void addAdvertisingBanner(@RequestBody GoodsShopMarkupAdvertisingBannerRequest request) {
        goodsShopMarkupElementService.processAdvertisingBannerRequest(request);
    }

    @PostMapping("create-view")
    private void addAdvertisementView(@RequestBody GoodsShopMarkupAdvertisementViewRequest request) {
        goodsShopMarkupElementService.processAdvertisementViewRequest(request);
    }

    @PostMapping("create-row")
    private void addAdvertisementRow(@RequestBody GoodsShopMarkupAdvertisementRowRequest request) {
        goodsShopMarkupElementService.processAdvertisementRowRequest(request);
    }

    @GetMapping("banner")
    private GoodsShopMarkupAdvertisingBannerResponse getBannerByElementId(Long id) {
        return new GoodsShopMarkupAdvertisingBannerResponse(goodsShopMarkupElementService.getBannerByElementId(id));
    }

    @GetMapping("view")
    private GoodsShopMarkupAdvertisementViewResponse getViewByElementId(Long id) {
        return goodsShopMarkupElementService.getViewResponseByElementId(id);
    }

    @GetMapping("row")
    private GoodsShopMarkupAdvertisementRowResponse getRowByElementId(Long id) {
        return goodsShopMarkupElementService.getRowResponseByElementId(id);
    }

    @GetMapping()
    private GoodsShopMarkupResponse getMarkupByGoodsSeller(Long id) {
        return new GoodsShopMarkupResponse(goodsSellerAccountService.findById(id).getMarkup());
    }

}
