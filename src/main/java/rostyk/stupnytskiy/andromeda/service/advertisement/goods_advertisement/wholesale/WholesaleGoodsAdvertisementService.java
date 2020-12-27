package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesaleGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;

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
    private FileTool fileTool;

    public void createAdvertisement(WholesaleGoodsAdvertisementRequest request) throws IOException {
        wholesalePriceService.validWholesaleUnit(request.getPrice());
        WholesaleGoodsAdvertisement advertisement = saveWholesaleGoodsAdvertisementRequest(request);
        request.getProperties().forEach(p -> propertyService.save(p, advertisement));
        wholesalePriceService.save(request.getPrice(),advertisement);
//        retailPriceService.save(request.getPrice(), advertisement);
    }

    public WholesaleGoodsAdvertisement saveWholesaleGoodsAdvertisementRequest(WholesaleGoodsAdvertisementRequest request) throws IOException {
        WholesaleGoodsAdvertisement advertisement = new WholesaleGoodsAdvertisement();
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();

        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        advertisement.setCurrency(currencyService.findById(request.getCurrencyId()));
        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());
        advertisement.setSeller(seller);
        advertisement.setCount(request.getCount());

        if (request.getMainImage() != null)
            advertisement.setMainImage(fileTool.saveAdvertisementImage(
                    request.getMainImage(),seller.getLogin())
            );
        if (request.getImages() != null){
            request.getImages().forEach(img -> {
                try {
                    request.getImages().add(fileTool.saveAdvertisementImage(img, seller.getLogin()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return advertisementRepository.save(advertisement);
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
}
