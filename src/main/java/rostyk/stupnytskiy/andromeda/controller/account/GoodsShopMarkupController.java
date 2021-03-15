package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisementRowRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisementViewRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisingBannerRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupLineRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.GoodsShopMarkupResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisementRowResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisementViewResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisingBannerResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.markup.GoodsShopMarkupElementService;
import rostyk.stupnytskiy.andromeda.service.account.seller.markup.GoodsShopMarkupLineService;
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

    @Autowired
    private GoodsShopMarkupLineService goodsShopMarkupLineService;

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

    @PostMapping("create-line")
    private void createLine(@RequestBody GoodsShopMarkupLineRequest request) {
        goodsShopMarkupElementService.createLine(request);
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

    @PutMapping("up")
    private void lineUp(Long id) {
        goodsShopMarkupLineService.lineUp(id);
    }

    @PutMapping("down")
    private void lineDown(Long id) {
        goodsShopMarkupLineService.lineDown(id);
    }

    @DeleteMapping("line")
    private void deleteLine(Long id) {
        goodsShopMarkupElementService.deleteElementsAndLine(id);
    }

    @PutMapping("make-empty")
    private void makeElementEmpty(Long id) {
        goodsShopMarkupElementService.makeElementEmpty(id);
    }
}
