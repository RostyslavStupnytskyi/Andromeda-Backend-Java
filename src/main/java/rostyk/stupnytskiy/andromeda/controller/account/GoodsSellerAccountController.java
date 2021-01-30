package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.GoodsSellerProfileResponse;
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

    @GetMapping("profile")
    public GoodsSellerProfileResponse getGoodsSellerAccountProfile(Long id) {
        return new GoodsSellerProfileResponse(goodsSellerAccountService.findById(id));
    }

    @GetMapping("stat")
    public void get() {
        goodsSellerAccountService.addStatisticsForEach();
    }
}
