package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/goods-seller")
public class GoodsSellerController {

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;


    @PutMapping("/update")
    public void updateGoodsSellerData(@Valid @RequestBody GoodsSellerDataRequest request){
        goodsSellerAccountService.updateGoodsSellerData(request);
    }
}
