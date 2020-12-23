package rostyk.stupnytskiy.andromeda.service.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.AccountService;
import rostyk.stupnytskiy.andromeda.service.CategoryService;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail.RetailPriceService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale.WholesalePriceService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private RetailPriceService retailPriceService;

    @Autowired
    private WholesalePriceService wholesalePriceService;

    @Autowired
    private CurrencyService currencyService;

//    @Autowired
//    private AdvertisementStatisticsService advertisementStatisticsService;

    @Autowired
    private FileTool fileTool;



    public void getOne(Long id) {
        Advertisement advertisement = findById(id);
        System.out.println(advertisement.getClass());
        System.out.println(advertisement);
    }

    public void createAdvertisement() {

        WholesaleGoodsAdvertisement goodsAdvertisement = new WholesaleGoodsAdvertisement();

        goodsAdvertisement.setTitle("Title2");
        goodsAdvertisement.setDescription("sescription2");
        goodsAdvertisement.setOnlySellerCountry(true);
        advertisementRepository.save(goodsAdvertisement);

        RetailGoodsAdvertisement advertisement = new RetailGoodsAdvertisement();

        advertisement.setTitle("Title3");
        advertisement.setDescription("sescription2");
        advertisement.setOnlySellerCountry(true);
        advertisementRepository.save(advertisement);


//    public void createAdvertisement(AdvertisementCreationRequest request) {
//        Advertisement advertisement = advertisementRepository.save(advertisementCreationRequestToAdvertisement(request));
//        advertisementStatisticsService.addRetailPriceToAdvertisementStatisticsFromAdvertisement(advertisement);
//        Advertisement advertisement = advertisementCreationRequestToAdvertisement(request);
//
//        request.getProperties().forEach((p) -> propertyService.save(p, advertisement));
//
//        if (request.getIsRetail()) retailPriceService.save(request.getRetailPriceRequest(), advertisement);
//        else wholesalePriceService.save(request.getWholesalePriceRequest(), advertisement);
    }

//    private Advertisement advertisementCreationRequestToAdvertisement(AdvertisementCreationRequest request) {
//
//        Advertisement advertisement = new Advertisement();
//
//        advertisement.setTitle(request.getTitle());
//        advertisement.setDescription(request.getDescription());
//
//        if (request.getCategoryId() != null)
//            advertisement.setCategory(categoryService.findById(request.getCategoryId()));
//        else advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
//
//        advertisement.setIsRetail(request.getIsRetail());
//        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());
//
//        advertisement.setStatistics(AdvertisementStatistics.builder().creationDate(LocalDateTime.now()).build());
//
//        advertisement.setCurrency(currencyService.findById(request.getCurrencyId()));
//
//         TODO add seller adding to advertisement
//
//        return advertisementRepository.save(advertisement);
//    }

    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No advertisement with id " + id));
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
