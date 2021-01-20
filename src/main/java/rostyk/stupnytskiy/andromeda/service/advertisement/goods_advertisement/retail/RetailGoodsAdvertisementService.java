package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailPriceRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailPrice;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.RetailGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;

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

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private FileTool fileTool;

    public RetailPrice finishAdvertisementCreation(RetailGoodsAdvertisement advertisement, RetailGoodsAdvertisementRequest request) {
        if (request.getProperties() != null)
            request.getProperties().forEach(p -> propertyService.save(p, advertisement));
        return retailPriceService.save(request.getPrice(), advertisement);
    }

    public void addNewWRetailPriceToRetailGoodsAdvertisement(RetailPriceRequest request, Long id) throws IllegalAccessException {
        RetailGoodsAdvertisement advertisement = findById(id);
        if (goodsSellerAccountService.findBySecurityContextHolder() == advertisement.getSeller()) {
            RetailPrice price = retailPriceService.save(request, advertisement);
            advertisement.setPriceToSort(price.getPrice());
            advertisementRepository.save(advertisement);
        } else throw new IllegalAccessException();
    }

    public RetailGoodsAdvertisement findById(Long id) {
        return retailGoodsAdvertisementRepository.findById(id).orElseThrow(IllegalReceiveException::new);
    }


}
