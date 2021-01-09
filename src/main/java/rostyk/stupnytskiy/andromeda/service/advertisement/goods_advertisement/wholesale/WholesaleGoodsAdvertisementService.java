package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesaleGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;

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

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private FileTool fileTool;


    public WholesalePrice finishAdvertisementCreation(WholesaleGoodsAdvertisement advertisement, WholesaleGoodsAdvertisementRequest request) {
        if (request.getProperties() != null)
            request.getProperties().forEach(p -> propertyService.save(p, advertisement));
        return wholesalePriceService.save(request.getPrice(),advertisement);
    }

    public void addNewWholesalePriceToWholesaleGoodsAdvertisement(WholesalePriceRequest request, Long id) throws IllegalAccessException {
        if (goodsSellerAccountService.findBySecurityContextHolder() == findById(id).getSeller()) wholesalePriceService.save(request,findById(id));
        else throw new IllegalAccessException();

        wholesalePriceService.validWholesaleUnit(request);
        wholesalePriceService.save(request,findById(id));
    }

    public WholesaleGoodsAdvertisement findById(Long id){
        return wholesaleGoodsAdvertisementRepository.findById(id).orElseThrow(IllegalReceiveException::new);
    }

    public void validWholesaleUnit(WholesalePriceRequest request) {
        wholesalePriceService.validWholesaleUnit(request);
    }
}
