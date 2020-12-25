package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailPriceRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.RetailGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;

@Service
public class RetailGoodsAdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private RetailGoodsAdvertisementRepository retailGoodsAdvertisementRepository;

    @Autowired
    private RetailPriceService retailPriceService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private PropertyService propertyService;

    public void createAdvertisement(RetailGoodsAdvertisementRequest request){

        RetailGoodsAdvertisement advertisement = saveRetailGoodsAdvertisementRequest(request);
        request.getProperties().forEach(p -> propertyService.save(p, advertisement));
        retailPriceService.save(request.getPrice(), advertisement);
    }

    public RetailGoodsAdvertisement saveRetailGoodsAdvertisementRequest(RetailGoodsAdvertisementRequest request){
        RetailGoodsAdvertisement advertisement = new RetailGoodsAdvertisement();
        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        advertisement.setCurrency(currencyService.findById(request.getCurrencyId()));
        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());
        advertisement.setSeller(goodsSellerAccountService.findBySecurityContextHolder());
        advertisement.setCount(request.getCount());
        return advertisementRepository.save(advertisement);
    }

    public void addNewWRetailPriceToRetailGoodsAdvertisement(RetailPriceRequest request, Long id){
        retailPriceService.save(request,findById(id));
    }

    public RetailGoodsAdvertisement findById(Long id){
        return retailGoodsAdvertisementRepository.findById(id).orElseThrow(IllegalReceiveException::new);
    }


}
