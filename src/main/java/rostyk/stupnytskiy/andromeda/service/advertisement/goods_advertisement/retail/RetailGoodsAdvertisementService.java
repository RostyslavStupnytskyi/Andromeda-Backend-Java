package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailPriceRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.RetailGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

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
    private FileTool fileTool;

    public void createAdvertisement(RetailGoodsAdvertisementRequest request) throws IOException {

        RetailGoodsAdvertisement advertisement = saveRetailGoodsAdvertisementRequest(request);
        request.getProperties().forEach(p -> propertyService.save(p, advertisement));
        retailPriceService.save(request.getPrice(), advertisement);
    }

    public RetailGoodsAdvertisement saveRetailGoodsAdvertisementRequest(RetailGoodsAdvertisementRequest request) throws IOException {
        RetailGoodsAdvertisement advertisement = new RetailGoodsAdvertisement();
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();

        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        advertisement.setCurrency(currencyService.findById(request.getCurrencyId()));
        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());
        advertisement.setSeller(goodsSellerAccountService.findBySecurityContextHolder());
        advertisement.setCount(request.getCount());

        if (request.getMainImage() != null)
            advertisement.setMainImage(fileTool.saveAdvertisementImage (
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

    public void addNewWRetailPriceToRetailGoodsAdvertisement(RetailPriceRequest request, Long id) throws IllegalAccessException {
        if (goodsSellerAccountService.findBySecurityContextHolder() == findById(id).getSeller()) retailPriceService.save(request,findById(id));
        else throw new IllegalAccessException();
    }

    public RetailGoodsAdvertisement findById(Long id){
        return retailGoodsAdvertisementRepository.findById(id).orElseThrow(IllegalReceiveException::new);
    }


}
