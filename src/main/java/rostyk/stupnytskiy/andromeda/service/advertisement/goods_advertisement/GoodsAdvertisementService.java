package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.PropertyRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementsForMainPageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.category.CategoryResponse;
import rostyk.stupnytskiy.andromeda.entity.Category;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.Property;

import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CategoryService;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.statistics.account.goods_seller.GoodsSellerStatisticsService;
import rostyk.stupnytskiy.andromeda.service.statistics.account.user.UserStatisticsService;
import rostyk.stupnytskiy.andromeda.service.statistics.advertisement.GoodsAdvertisementStatisticsService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
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
    private CurrencyService currencyService;

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
    private UserStatisticsService userStatisticsService;

    @Autowired
    private GoodsAdvertisementStatisticsService goodsAdvertisementStatisticsService;

    @Autowired
    private GoodsSellerStatisticsService goodsSellerStatisticsService;


    public GoodsAdvertisement findById(Long id) {
        return goodsAdvertisementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsAdvertisement findByIdAndSeller(Long id) {
        return goodsAdvertisementRepository.findByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder()).orElseThrow(IllegalArgumentException::new);
    }
    public void setAdvertisementView(Long id) {
        userStatisticsService.saveUserAdvertisementViewRequest(findById(id));
        goodsAdvertisementStatisticsService.incrementGoodsAdvertisementViews(id);
        goodsSellerStatisticsService.incrementMonthStatisticsAdvertisementViews(findById(id).getSeller());
    }

    public GoodsAdvertisement goodsAdvertisementFromRequest(GoodsAdvertisementRequest request) {
        GoodsAdvertisement advertisement = new GoodsAdvertisement();
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();

        advertisement.setTitle(request.getTitle());
        if (!request.getDescription().isEmpty()) advertisement.setDescription(request.getDescription());

        advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());
        advertisement.setSeller(goodsSellerAccountService.findBySecurityContextHolder());
//        advertisement.setCount(request.getCount());
        advertisement.setStatistics(new GoodsAdvertisementStatistics());

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


    public void minusAdvertisementCount(Long goodsAdvertisementId, Integer minus) {
        GoodsAdvertisement advertisement = findById(goodsAdvertisementId);
//        advertisement.setCount(advertisement.getCount() - minus);
        goodsAdvertisementRepository.save(advertisement);
    }

    public PageResponse<AdvertisementResponse> findAllSellerAdvertisementsPage(Long id, PaginationRequest request) {
        if (id == null) {
            id = goodsSellerAccountService.findBySecurityContextHolder().getId();
        }
        Page<GoodsAdvertisement> page = goodsAdvertisementRepository.findPageBySellerId(id, request.mapToPageable());
        return new PageResponse<>(page
                .get()
                .map(GoodsAdvertisementResponse::new)
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

        goodsAdvertisementStatisticsService.incrementGoodsAdvertisementInLikeLists(id);
    }

    public void removeFromFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        GoodsAdvertisement goodsAdvertisement = findById(id);
        user.getFavoriteAdvertisements().remove(goodsAdvertisement);
        userAccountService.save(user);
        goodsAdvertisementStatisticsService.decrementGoodsAdvertisementInLikeLists(id);
    }

    public Boolean isInFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        GoodsAdvertisement goodsAdvertisement = findById(id);
        return user.getFavoriteAdvertisements().contains(goodsAdvertisement);
    }

    public PageResponse<AdvertisementResponse> findAllFavoriteAdvertisementPage(PaginationRequest request) {
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

    public GoodsAdvertisementMonthStatistics findMonthStatisticsByIdAndMonthAndYear(Long id, Integer month, Integer year) {
        Month m = Month.of(month + 1);
        return goodsAdvertisementStatisticsService.getMonthStatisticsByGoodsAdvertisementStatisticsAndMonthAndYear(findById(id).getStatistics(), m, year);
    }

    public GoodsAdvertisementsForMainPageResponse getGoodsAdvertisementsForMainPage(PaginationRequest request) {
        UserAccount userAccount = userAccountService.findBySecurityContextHolder();
        GoodsAdvertisementsForMainPageResponse response = new GoodsAdvertisementsForMainPageResponse();
        Category category;
        if (userAccount != null) {
            category = userStatisticsService.defineMostCommonCategoryOfLastTwentyViews();
            if (category == null) category = categoryService.getRandomCategory();
        } else {
            category = categoryService.getRandomCategory();
            category = categoryService.findById(4L);
        }
        response.setCategory(new CategoryResponse(category));
        response.setResponses(getAdvertisementsResponsePageByCategory(category, request));
        return response;
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


}
