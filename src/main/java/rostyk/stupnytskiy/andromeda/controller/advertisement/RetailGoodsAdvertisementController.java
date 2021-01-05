package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailPriceRequest;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/retail-goods")
public class RetailGoodsAdvertisementController {

    @Autowired
    private RetailGoodsAdvertisementService retailGoodsAdvertisementService;

    @PostMapping
    private void createRetail(@Valid @RequestBody RetailGoodsAdvertisementRequest request) throws IOException {
        request.getImages().forEach((i) -> System.out.println("image"));
        retailGoodsAdvertisementService.createAdvertisement(request);
    }

    @PutMapping("/change-price")
    private void changeAdvertisementPrice(@RequestBody RetailPriceRequest request, Long id) throws IllegalAccessException {
        retailGoodsAdvertisementService.addNewWRetailPriceToRetailGoodsAdvertisement(request, id);
    }
}
