package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/retail-goods")
public class RetailGoodsAdvertisementController {

    @Autowired
    private RetailGoodsAdvertisementService retailGoodsAdvertisementService;


    @PostMapping
    private void createRetail(@Valid @RequestBody RetailGoodsAdvertisementRequest request){
        retailGoodsAdvertisementService.createAdvertisement(request);
    }
}
