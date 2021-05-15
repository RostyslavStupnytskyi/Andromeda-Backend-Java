package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementSearchRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.service.advertisement.AdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter.ParameterService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private ParameterService parameterService;


    @GetMapping()
    private GoodsAdvertisementResponse getOneById(Long id) {
        return goodsAdvertisementService.getResponseByAdvertisementId(id);
    }

    @GetMapping("seller")
    private PageResponse<GoodsAdvertisementForSearchResponse> getAllSellerAdvertisementsPage(Long id, PaginationRequest request) {
        return goodsAdvertisementService.findAllSellerAdvertisementsPage(id, request);
    }

    @GetMapping("favorites")
    private PageResponse<GoodsAdvertisementResponse> getUserFavoriteAdvertisementsPage(PaginationRequest request) {
        return goodsAdvertisementService.findAllFavoriteAdvertisementPage(request);
    }

    @PostMapping("filter")
    public PageResponse<GoodsAdvertisementForSearchResponse> findForSearch(@RequestBody GoodsAdvertisementSearchRequest request) {
        return goodsAdvertisementService.findPageBySearchRequest(request); //(request);
    }

    @GetMapping("/count")
    private Integer getAdvertisementCount(Long paramsId) {
        return parameterService.findParametersValuesPriceCountById(paramsId).getCount();
    }

    @PostMapping
    private void createGoodsAdvertisement(@RequestBody GoodsAdvertisementRequest request) {
        goodsAdvertisementService.saveGoodsAdvertisement(request);
    }

    @GetMapping("find-by-value")
    public List<GoodsAdvertisementForSearchResponse> getAdvertisementsByIdOrTitleContains(String value, Long sellerId) {
        return goodsAdvertisementService.findSellerAdvertisementsByIdOrTitleContains(value, sellerId);
    }

    @PutMapping("add-to-favorites")
    public void addToFavorites(Long id) {
        goodsAdvertisementService.addToFavorites(id);
    }

    @PutMapping("remove-from-favorites")
    public void removeFromFavorites(Long id) {
        goodsAdvertisementService.removeFromFavorites(id);
    }

    @GetMapping("is-in-favorites")
    public Boolean isInFavorites(Long id) {
        return goodsAdvertisementService.isInFavorites(id);
    }

    @PutMapping("view")
    public void setAdvertisementView(Long id) {
        goodsAdvertisementService.setAdvertisementView(id);
    }


}
