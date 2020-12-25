package rostyk.stupnytskiy.andromeda.service.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private GoodsAdvertisementRepository goodsAdvertisementRepository;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

//    @Autowired
//    private AdvertisementStatisticsService advertisementStatisticsService;

    @Autowired
    private FileTool fileTool;

    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No advertisement with id " + id));
    }

    public GoodsAdvertisement findGoodsAdvertisementById(Long id) {
        return goodsAdvertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No advertisement with id " + id));
    }

    //TODO
//    private void saveAdvertisementImages(AdvertisementCreationRequest request, Long id) throws IOException {
//        Advertisement advertisement = findById(id);
//        Account account = accountService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        if (request.getMainImage() != null) {
//            advertisement.setMainImage(fileTool.savePublicationImage(request.getMainImage(), account.getLogin(), id));
//        }
//        List<String> images = new ArrayList<>();
//        for (String image : request.getImages()) {
//            images.add(fileTool.savePublicationImage(image, account.getLogin(), id));
//        }
//        advertisement.setImages(images);
//        advertisementRepository.save(advertisement);
//    }

//    public void changeAdvertisementPrice(AdvertisementChangePriceRequest request, Long id) {
//        Advertisement advertisement = findById(id);

//        if (advertisement.getIsRetail()) retailPriceService.save(request.getRetailPriceRequest(), advertisement);
//        else wholesalePriceService.save(request.getWholesalePriceRequest(), advertisement); TODO
//    }

}
