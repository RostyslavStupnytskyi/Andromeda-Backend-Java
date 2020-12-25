package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.RetailGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesaleGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.AdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail.RetailPriceService;
import rostyk.stupnytskiy.andromeda.service.seller.GoodsSellerAccountService;

@Service
public class WholesaleGoodsAdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private WholesaleGoodsAdvertisementRepository wholesaleGoodsAdvertisementRepository;

    @Autowired
    private WholesalePriceService wholesalePriceService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private PropertyService propertyService;

    public void createAdvertisement(WholesaleGoodsAdvertisementRequest request){
        wholesalePriceService.validWholesaleUnit(request.getPrice());
        WholesaleGoodsAdvertisement advertisement = saveWholesaleGoodsAdvertisementRequest(request);
        request.getProperties().forEach(p -> propertyService.save(p, advertisement));
        wholesalePriceService.save(request.getPrice(),advertisement);
//        retailPriceService.save(request.getPrice(), advertisement);
    }

    public WholesaleGoodsAdvertisement saveWholesaleGoodsAdvertisementRequest(WholesaleGoodsAdvertisementRequest request){
        WholesaleGoodsAdvertisement advertisement = new WholesaleGoodsAdvertisement();
        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        advertisement.setCurrency(currencyService.findById(request.getCurrencyId()));
        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());
        advertisement.setSeller(goodsSellerAccountService.findBySecurityContextHolder());
        return advertisementRepository.save(advertisement);
    }

    public void addNewWholesalePriceToWholesaleGoodsAdvertisement(WholesalePriceRequest request, Long id){
        wholesalePriceService.validWholesaleUnit(request);
        wholesalePriceService.save(request,findById(id));
    }

    public WholesaleGoodsAdvertisement findById(Long id){
        return wholesaleGoodsAdvertisementRepository.findById(id).orElseThrow(IllegalReceiveException::new);
    }
}
