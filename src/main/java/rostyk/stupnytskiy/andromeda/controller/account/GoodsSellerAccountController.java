package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.ImageRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.goods_seller.GoodsSellerMainDaraRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.goods_seller.seller_categories.GoodsSellerAdvertisementCategoryRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.goods_seller.GoodsSellerDataResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.goods_seller.GoodsSellerProfileResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.goods_seller.category.GoodsSellerAdvertisementCategoryWithChildrenResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller.GoodsSellerMonthStatisticsResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller.GoodsSellerStatisticsResponse;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAdvertisementCategoryService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/goods-seller")
public class GoodsSellerAccountController {

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private GoodsSellerAdvertisementCategoryService goodsSellerAdvertisementCategoryService;


    @PutMapping("/update")
    public void updateGoodsSellerData(@Valid @RequestBody GoodsSellerDataRequest request) {
        goodsSellerAccountService.updateGoodsSellerData(request);
    }

    @PutMapping("/main-data")
    public void updateGoodsSellerMainData(@Valid @RequestBody GoodsSellerMainDaraRequest request) {
        goodsSellerAccountService.updateGoodsSellerMainData(request);
    }

    @GetMapping("profile-data")
    public GoodsSellerProfileResponse getGoodsSellerAccount() {
        return new GoodsSellerProfileResponse(goodsSellerAccountService.findBySecurityContextHolder());
    }

    @GetMapping("statistics")
    public GoodsSellerStatisticsResponse getGoodsSellerStatistics() {
        return new GoodsSellerStatisticsResponse();
    }

    @GetMapping("month-statistics")
    public GoodsSellerMonthStatisticsResponse getGoodsSellerMonthStatistics(Integer month, Integer year) {
        return new GoodsSellerMonthStatisticsResponse();
    }

    @GetMapping("profile")
    public GoodsSellerProfileResponse getGoodsSellerAccountProfile(Long id) {
        return new GoodsSellerProfileResponse(goodsSellerAccountService.findById(id));
    }

    @GetMapping("data")
    public GoodsSellerDataResponse getGoodsSellerData() {
        return goodsSellerAccountService.getGoodsSellerData();
    }

    @PostMapping("create-category")
    public void createGoodsSellerCategory(@RequestBody GoodsSellerAdvertisementCategoryRequest request) {
        goodsSellerAdvertisementCategoryService.save(request);
    }

    @PutMapping("change-category-title")
    public void changeCategoryTitle(@RequestBody GoodsSellerAdvertisementCategoryRequest request, Long id) {
        goodsSellerAdvertisementCategoryService.update(request, id);
    }

    @GetMapping("category-tree")
    public List<GoodsSellerAdvertisementCategoryWithChildrenResponse> getCategoriesTree(Long id) {
        return goodsSellerAdvertisementCategoryService.getSellerCategoriesTree(id);
    }

    @PutMapping("banner")
    public void changeGoodsSellerBanner(@RequestBody ImageRequest request) {
        goodsSellerAccountService.changeGoodsSellerBanner(request);
    }

}
