package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementSearchRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.service.advertisement.AdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @GetMapping
    private <T extends AdvertisementResponse> AdvertisementResponse findOneById(Long id) {
        return advertisementService.findById(id).mapToResponse();
    }

    @PostMapping("retail")
    private void createRetail(@Valid @RequestBody RetailGoodsAdvertisementRequest request) {
        goodsAdvertisementService.saveRetailGoodsAdvertisement(request);
    }

    @PostMapping("/wholesale")
    public void save(@Valid @RequestBody WholesaleGoodsAdvertisementRequest request) {
        goodsAdvertisementService.saveWholesaleGoodsAdvertisement(request);
    }

    @GetMapping("/filter")
    public PageResponse<GoodsAdvertisementForSearchResponse> findForSearch(GoodsAdvertisementSearchRequest request) {
        return advertisementService.findPageBySearchRequest(request); //(request);
    }

    @PutMapping
    public void test(){
        goodsAdvertisementService.exchangePriceForAll();
    }


//    @PutMapping
//    private void addStatistics() {
//        advertisementService.addStatisticsToAll();
//    }


//    @PostMapping
//    private void create(@Valid @RequestBody AdvertisementCreationRequest request){
//        advertisementService.createAdvertisement(request);
//    }
//
//    @PutMapping("/change-price")
//    private void changeAdvertisementPrice(@Valid @RequestBody AdvertisementChangePriceRequest request, Long id){
//        advertisementService.changeAdvertisementPrice(request, id);
//    }

//    @GetMapping
//    private AdvertisementResponse test(Long id){
//        return new AdvertisementResponse(advertisementService.findById(id));
//    }
}
