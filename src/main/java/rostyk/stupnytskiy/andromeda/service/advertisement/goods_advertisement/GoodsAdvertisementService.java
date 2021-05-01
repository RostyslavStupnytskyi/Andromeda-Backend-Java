package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.PropertyRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementsForMainPageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.category.CategoryResponse;
import rostyk.stupnytskiy.andromeda.entity.Category;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.Property;

import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;
import rostyk.stupnytskiy.andromeda.repository.advertisement.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.GoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.statistics.account.UserAdvertisementViewRepository;
import rostyk.stupnytskiy.andromeda.service.CategoryService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.discount.DiscountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter.ParameterService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsAdvertisementService {

    @Autowired
    private GoodsAdvertisementRepository goodsAdvertisementRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private FileTool fileTool;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserAdvertisementViewRepository userAdvertisementViewRepository;


    public GoodsAdvertisement findById(Long id) {
        System.out.println(id);
        return goodsAdvertisementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsAdvertisement findByIdAndSeller(Long id) {
        return goodsAdvertisementRepository.findByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder()).orElseThrow(IllegalArgumentException::new);
    }

    public void saveGoodsAdvertisement(GoodsAdvertisementRequest request) {
        GoodsAdvertisement goodsAdvertisement = goodsAdvertisementRepository.save(goodsAdvertisementFromRequest(request));
        if (request.getProperties() != null) {
            request.getProperties().forEach((p) -> propertyService.save(p, goodsAdvertisement));
        }
        if (request.getHasParameters()) {
            request.getParameters().forEach((p) -> parameterService.saveParameter(p, goodsAdvertisement));
            request.getValuesPriceCounts().forEach((p) -> parameterService.saveParametersValuePriceCount(p, goodsAdvertisement));
        } else {
            parameterService.saveParametersValuePriceCountWithoutParameters(request.getValuesPriceCounts().get(0), goodsAdvertisement);
        }
    }


    public PageResponse<GoodsAdvertisementForSearchResponse> findAllSellerAdvertisementsPage(Long id, PaginationRequest request) {
        if (id == null) {
            id = goodsSellerAccountService.findBySecurityContextHolder().getId();
        }
        Page<GoodsAdvertisement> page = goodsAdvertisementRepository.findPageBySellerId(id, request.mapToPageable());
        return new PageResponse<>(page
                .get()
                .map(GoodsAdvertisementForSearchResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<GoodsAdvertisementResponse> findAllFavoriteAdvertisementPage(PaginationRequest request) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        Page<GoodsAdvertisement> page = goodsAdvertisementRepository.getAllAdvertisementByUserId(user.getId(), request.mapToPageable());

        return new PageResponse<>(page
                .get()
                .map(GoodsAdvertisementResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private PageResponse<GoodsAdvertisementForSearchResponse> getAdvertisementsResponsePageByCategory(Category category, PaginationRequest request) {
        Page<GoodsAdvertisement> page = goodsAdvertisementRepository.getAllBySubcategoryCategory(category.getTitle(), request.mapToPageable());

        return new PageResponse<>(page
                .get()
                .map(GoodsAdvertisementForSearchResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private PageResponse<GoodsAdvertisementForSearchResponse> mapPageToSearchPageResponse(Page<GoodsAdvertisement> page) {
        return new PageResponse<>(page
                .get()
                .map(GoodsAdvertisementForSearchResponse::new)
//                .peek((d) -> d.setHasDiscount())
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void changeAdvertisementTitle(Long id, String title) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        advertisement.setTitle(title);
        goodsAdvertisementRepository.save(advertisement);
    }

    public void changeAdvertisementCount(Long id, Integer count) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
//        advertisement.setCount(count);
        goodsAdvertisementRepository.save(advertisement);
    }

    public void changeAdvertisementDescription(Long id, String description) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        advertisement.setDescription(description);
        goodsAdvertisementRepository.save(advertisement);
    }

    public void changeAdvertisementProperties(Long id, List<PropertyRequest> properties) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        List<Property> propertyList = new ArrayList<>();

        advertisement.getProperties().forEach((p) ->
                propertyService.delete(p.getId())
        );
        if (properties != null) {
            properties.forEach(p -> propertyService.save(p, advertisement));
        }
//        goodsAdvertisementRepository.save(advertisement).getProperties().forEach(p -> System.out.println(p.toString()));

    }

    public void changeAdvertisementDeliveries(Long id, List<Long> deliveryIds) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        advertisement.setDeliveryTypes(new ArrayList<>());
        if (deliveryIds != null)
            deliveryIds.forEach((t) -> advertisement.getDeliveryTypes().add(deliveryTypeService.findById(t)));
        goodsAdvertisementRepository.save(advertisement);
    }

    public void changeAdvertisementOnlySellerCountry(Long id, Boolean isOnly) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        advertisement.setOnlySellerCountry(isOnly);
        goodsAdvertisementRepository.save(advertisement);
    }

    public String addImageToGoodsAdvertisement(Long id, String image) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        String imageName = null;
        try {
            imageName = fileTool.saveAdvertisementImage(image, advertisement.getSeller().getId());
            if (advertisement.getMainImage() == null) advertisement.setMainImage(imageName);
            else advertisement.getImages().add(imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        goodsAdvertisementRepository.save(advertisement);
        return imageName;
    }

    public void makeMainImageToGoodsAdvertisement(Long id, String imageName) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        advertisement.getImages().add(advertisement.getMainImage());
        advertisement.getImages().remove(imageName);
        advertisement.setMainImage(imageName);
        goodsAdvertisementRepository.save(advertisement);
    }

    public void deleteGoodsAdvertisementImage(Long id, String imageName) {
        GoodsAdvertisement advertisement = findByIdAndSeller(id);
        advertisement.getImages().remove(imageName);
        fileTool.deleteAdvertisementImage(imageName, advertisement.getSeller().getId());
        goodsAdvertisementRepository.save(advertisement);
    }

    public void addToFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        GoodsAdvertisement goodsAdvertisement = findById(id);
        if (!user.getFavoriteAdvertisements().contains(goodsAdvertisement)) {
            user.getFavoriteAdvertisements().add(goodsAdvertisement);
            userAccountService.save(user);
        }

    }

    public void removeFromFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        GoodsAdvertisement goodsAdvertisement = findById(id);
        user.getFavoriteAdvertisements().remove(goodsAdvertisement);
        userAccountService.save(user);
    }

    public Boolean isInFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        if (user != null) {
            GoodsAdvertisement goodsAdvertisement = findById(id);
            return user.getFavoriteAdvertisements().contains(goodsAdvertisement);
        }
        return false;
    }

    public GoodsAdvertisementsForMainPageResponse getGoodsAdvertisementsForMainPage(PaginationRequest request) {
        UserAccount userAccount = userAccountService.findBySecurityContextHolder();
        GoodsAdvertisementsForMainPageResponse response = new GoodsAdvertisementsForMainPageResponse();
        Category category = null;
        if (userAccount != null) {
//            category = userStatisticsService.defineMostCommonCategoryOfLastTwentyViews();
            if (category == null) category = categoryService.getRandomCategory();
        } else {
            category = categoryService.getRandomCategory();
            category = categoryService.findById(4L);
        }
        response.setCategory(new CategoryResponse(category));
        response.setResponses(getAdvertisementsResponsePageByCategory(category, request));
        return response;
    }

    public GoodsAdvertisement goodsAdvertisementFromRequest(GoodsAdvertisementRequest request) {
        GoodsAdvertisement advertisement = new GoodsAdvertisement();
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();

        advertisement.setTitle(request.getTitle());
        if (!request.getDescription().isEmpty()) advertisement.setDescription(request.getDescription());

        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));

        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());

        advertisement.setSeller(goodsSellerAccountService.findBySecurityContextHolder());

        advertisement.setHasParameters(request.getHasParameters());

        advertisement.setCreationDate(LocalDateTime.now());

        if (request.getHasParameters())
            advertisement.setPriceToSort(request.getValuesPriceCounts().stream().mapToDouble(ParametersValuesPriceCountRequest::getPrice).min().getAsDouble());
        else advertisement.setPriceToSort(request.getValuesPriceCounts().get(0).getPrice());

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


    public List<GoodsAdvertisement> getNewGoodsAdvertisementsForGoodsSellerProfile(GoodsSellerAccount goodsSeller) {
        return goodsAdvertisementRepository.findFirst5BySellerOrderByIdDesc(goodsSeller);
    }


    public List<GoodsAdvertisementForSearchResponse> findSellerAdvertisementsByIdOrTitleContains(String value, Long sellerId) {
        GoodsSellerAccount seller;
        if (sellerId != null) {
            seller = goodsSellerAccountService.findById(sellerId);
        } else {
            seller = goodsSellerAccountService.findBySecurityContextHolder();
        }
        return goodsAdvertisementRepository.findAllBySeller(seller)
                .stream()
                .filter((a) -> a.getTitle().toLowerCase().contains(value.toLowerCase()) ||
                        a.getTitle().toUpperCase().contains(value.toUpperCase()) ||
                        a.getId().toString().contains(value))
                .map(GoodsAdvertisementForSearchResponse::new)
                .collect(Collectors.toList());
    }

    public void rewriteDates() {
        List<GoodsAdvertisement> advertisements = goodsAdvertisementRepository.findAll();

    }

    public void setAdvertisementView(Long id) {
        UserAdvertisementView view = new UserAdvertisementView();
        view.setUser(userAccountService.findBySecurityContextHolder());
        view.setDateTime(LocalDateTime.now());
        view.setGoodsAdvertisement(findById(id));
        userAdvertisementViewRepository.save(view);
    }
}
