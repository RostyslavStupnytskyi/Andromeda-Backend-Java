package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailPrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesaleGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale.WholesalePriceService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class GoodsAdvertisementService {

    @Autowired
    private GoodsAdvertisementRepository goodsAdvertisementRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private WholesaleGoodsAdvertisementService wholesaleGoodsAdvertisementService;

    @Autowired
    private RetailGoodsAdvertisementService retailGoodsAdvertisementService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private FileTool fileTool;

    public GoodsAdvertisement findById(Long id) {
        return goodsAdvertisementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }


    public void saveRetailGoodsAdvertisement(RetailGoodsAdvertisementRequest request) {
        RetailGoodsAdvertisement advertisement =
                advertisementRepository.save(new RetailGoodsAdvertisement(goodsAdvertisementFromRequest(request)));
        RetailPrice price = retailGoodsAdvertisementService.finishAdvertisementCreation(advertisement, request);
        advertisement.setPriceInHryvnia(price.getPrice() * advertisement.getCurrency().getPriceInHryvnia());
        advertisementRepository.save(advertisement);
    }

    public void saveWholesaleGoodsAdvertisement(WholesaleGoodsAdvertisementRequest request) {
        wholesaleGoodsAdvertisementService.validWholesaleUnit(request.getPrice());
        WholesaleGoodsAdvertisement advertisement =
                advertisementRepository.save(new WholesaleGoodsAdvertisement(goodsAdvertisementFromRequest(request)));
        WholesalePrice price = wholesaleGoodsAdvertisementService.finishAdvertisementCreation(advertisement, request);
        advertisement.setPriceInHryvnia(price.getMinPrice() * advertisement.getCurrency().getPriceInHryvnia());
        advertisementRepository.save(advertisement);
    }

    public void exchangePriceForAll() {
        goodsAdvertisementRepository.findAll().forEach((a) -> {
            a.setPriceInHryvnia(
                    Math.round(a.getPriceForExchanging() * a.getCurrency().getPriceInHryvnia() * 100) / 100.0
            );
            goodsAdvertisementRepository.save(a);
        });
    }


    public GoodsAdvertisement goodsAdvertisementFromRequest(GoodsAdvertisementRequest request) {
        GoodsAdvertisement advertisement = new GoodsAdvertisement();
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();

        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        advertisement.setCurrency(currencyService.findById(request.getCurrencyId()));
        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());
        advertisement.setSeller(goodsSellerAccountService.findBySecurityContextHolder());
        advertisement.setCount(request.getCount());

        advertisement.setStatistics(GoodsAdvertisementStatistics.builder()
                .creationDate(LocalDateTime.now())
                .numberOfOrders(0)
                .views(0)
                .sold(0L)
                .build());

        if (request.getDeliveryTypes() != null)
            request.getDeliveryTypes().forEach((t) -> advertisement.getDeliveryTypes().add(deliveryTypeService.findById(t)));

        if (request.getMainImage() != null) {
            try {
                advertisement.setMainImage(fileTool.saveAdvertisementImage(
                        request.getMainImage(), seller.getId())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (request.getImages() != null) {
            request.getImages().forEach(img -> {
                try {
                    advertisement.getImages().add(fileTool.saveAdvertisementImage(img, seller.getId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return advertisement;
    }


}
