package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.GoodsSellerDataResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.GoodsSellerProfileResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller.GoodsSellerMonthStatisticsResponse;
import rostyk.stupnytskiy.andromeda.dto.response.statistics.account.seller.GoodsSellerStatisticsResponse;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/goods-seller")
public class GoodsSellerAccountController {

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;


    @PutMapping("/update")
    public void updateGoodsSellerData(@Valid @RequestBody GoodsSellerDataRequest request) {
        goodsSellerAccountService.updateGoodsSellerData(request);
    }

    @GetMapping
    public GoodsSellerProfileResponse getGoodsSellerAccount() {
        return new GoodsSellerProfileResponse(goodsSellerAccountService.findBySecurityContextHolder());
    }

    @GetMapping("statistics")
    public GoodsSellerStatisticsResponse getGoodsSellerStatistics() {
        return new GoodsSellerStatisticsResponse(goodsSellerAccountService.findBySecurityContextHolder());
    }

    @GetMapping("month-statistics")
    public GoodsSellerMonthStatisticsResponse getGoodsSellerMonthStatistics(Integer month, Integer year) {
        return new GoodsSellerMonthStatisticsResponse(goodsSellerAccountService.findMonthStatisticsByIdAndMonthAndYear(month, year));
    }

    @GetMapping("profile")
    public GoodsSellerProfileResponse getGoodsSellerAccountProfile(Long id) {
        return new GoodsSellerProfileResponse(goodsSellerAccountService.findById(id));
    }

    @GetMapping("data")
    public GoodsSellerDataResponse getGoodsSellerData() {
        return goodsSellerAccountService.getGoodsSellerData();
    }

    @GetMapping("stat")
    public void get() {
        goodsSellerAccountService.addStatisticsForEach();
    }
}
