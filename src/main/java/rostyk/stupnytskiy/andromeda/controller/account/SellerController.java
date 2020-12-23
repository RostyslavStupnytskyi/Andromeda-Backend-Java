package rostyk.stupnytskiy.andromeda.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.SellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.delivery.SellerDeliveryRequest;
import rostyk.stupnytskiy.andromeda.service.seller.SellerService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PutMapping("/update")
    private void updateSellerData(@Valid @RequestBody SellerDataRequest request){
        sellerService.updateSellerData(request);
    }



//    @PutMapping("/countries")
//    private void updaterSellerCountries(@Valid @RequestBody){
//        sellerService.updateSellerCountryCodes(request);
//    }

//    @PutMapping("/deliveries")
//    private void updaterSellerDeliveries(@Valid @RequestBody SellerDeliveryRequest request){
//        sellerService.updateSellerDeliveryCodes(request);
//    }
}