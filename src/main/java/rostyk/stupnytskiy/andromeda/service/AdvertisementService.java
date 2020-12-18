package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementCreationRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;
import rostyk.stupnytskiy.andromeda.entity.statistics.AdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.util.ArrayList;

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
    private FileTool fileTool;


    private void save(AdvertisementCreationRequest request){
        Advertisement advertisement;
    }

    private Advertisement createAdvertisement(AdvertisementCreationRequest request){

        Advertisement advertisement = new Advertisement();

        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());

        if (request.getCategoryId() != null) advertisement.setCategory(categoryService.findById(request.getCategoryId()));
        else advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));

        advertisement.setIsRetail(request.getIsRetail());
        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());

        advertisement.setProperties(new ArrayList<>());
        request.getProperties().forEach((p) -> advertisement.getProperties().add(propertyService.propertyRequestToProperty(p)));

        // TODO add seller adding to advertisement
        if (request.getIsRetail()) advertisement.setRetailPrice(retailPriceService.retailPriceRequestToRetailPrice(request.getRetailPriceRequest()));
        else advertisement.setWholesalePrice(wholesalePriceService.wholesalePriceRequestToWholesalePrice(request.getWholesalePriceRequest()));

        advertisement.setStatistics(new AdvertisementStatistics());
//        advertisement.getRetailPrices().add();
//        advertisement
        return advertisement;
    }

    private void addRetailPriceToAdvertisementStatistics(Long advertisementId){
        Advertisement advertisement = findById(advertisementId);
        AdvertisementStatistics statistics = advertisement.getStatistics();
        statistics.getRetailPrices().add(advertisement.getRetailPrice());

    }

//    private Advertisement advertisementRequestToAdvertisement(AdvertisementRequest request, Advertisement advertisement) {
//
//    }

    public Advertisement findById(Long id){
        return advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No advertisement with id " + id));
    }

    private void saveAdvertisementImages(AdvertisementCreationRequest request, Long id) throws IOException {
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
    }
}
