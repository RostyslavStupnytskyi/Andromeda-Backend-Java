package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementSearchRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;
import rostyk.stupnytskiy.andromeda.entity.statistics.UserAdvertisementView;
import rostyk.stupnytskiy.andromeda.repository.advertisement.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.GoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.feedback.GoodsAdvertisementFeedbackRepository;
import rostyk.stupnytskiy.andromeda.repository.statistics.UserAdvertisementViewRepository;
import rostyk.stupnytskiy.andromeda.service.CategoryService;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.discount.DiscountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter.ParameterService;
import rostyk.stupnytskiy.andromeda.specification.GoodsAdvertisementSpecification;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private GoodsAdvertisementFeedbackRepository goodsAdvertisementFeedbackRepository;

    @Autowired
    private CurrencyService currencyService;


    public GoodsAdvertisement findById(Long id) {
        System.out.println(id);
        return goodsAdvertisementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsAdvertisementResponse getResponseByAdvertisementId(Long id) {
        return new GoodsAdvertisementResponse(findById(id), currencyService.findCurrencyByCurrencyCode("USD"));
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
                .map(this::convertGoodsAdvertisementsToSearchResponse)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<GoodsAdvertisementResponse> findAllFavoriteAdvertisementPage(PaginationRequest request) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        Page<GoodsAdvertisement> page = goodsAdvertisementRepository.getAllAdvertisementByUserId(user.getId(), request.mapToPageable());

        return null;
//        return new PageResponse<>(page
//                .get()
//                .map(GoodsAdvertisementResponse::new)
//                .collect(Collectors.toList()),
//                page.getTotalElements(),
//                page.getTotalPages()
//        );
    }


    public void addToFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        GoodsAdvertisement goodsAdvertisement = findById(id);
        if (!user.getFavoriteAdvertisements().contains(goodsAdvertisement)) {
            user.getFavoriteAdvertisements().add(goodsAdvertisement);
            userAccountService.save(user);
        }

    }

    public void removeFromFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        GoodsAdvertisement goodsAdvertisement = findById(id);
        user.getFavoriteAdvertisements().remove(goodsAdvertisement);
        userAccountService.save(user);
    }

    public Boolean isInFavorites(Long id) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        if (user != null) {
            GoodsAdvertisement goodsAdvertisement = findById(id);
            return user.getFavoriteAdvertisements().contains(goodsAdvertisement);
        }
        return false;
    }


    public GoodsAdvertisement goodsAdvertisementFromRequest(GoodsAdvertisementRequest request) {
        GoodsAdvertisement advertisement = new GoodsAdvertisement();
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();

        advertisement.setTitle(request.getTitle());
        if (!request.getDescription().isEmpty()) advertisement.setDescription(request.getDescription());

        if (advertisement.getSubcategory() != null) {
            advertisement.setSubcategory(subcategoryService.findOneById(request.getSubcategoryId()));
        }

        advertisement.setOnlySellerCountry(request.getOnlySellerCountry());

        advertisement.setSeller(goodsSellerAccountService.findBySecurityContextHolder());

        advertisement.setHasParameters(request.getHasParameters());

        advertisement.setCreationDate(LocalDateTime.now());

        if (request.getHasParameters())
            advertisement.setPriceToSort(
                    request.getValuesPriceCounts().stream()
                            .mapToDouble((p) -> p.getPrices().get(0).getPrice()).min().getAsDouble());
        else advertisement.setPriceToSort(request.getValuesPriceCounts().get(0).getPrices().get(0).getPrice());

//        if (request.getDeliveryTypes() != null)
//            request.getDeliveryTypes().forEach((t) -> advertisement.getDeliveryTypes().add(deliveryTypeService.findById(t)));

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
                .map(this::convertGoodsAdvertisementsToSearchResponse)
                .collect(Collectors.toList());
    }

    public void setAdvertisementView(Long id) {
        UserAdvertisementView view = new UserAdvertisementView();
        view.setUser(userAccountService.findBySecurityContextHolderOrReturnNull());
        view.setDateTime(LocalDateTime.now());
        view.setGoodsAdvertisement(findById(id));
        userAdvertisementViewRepository.save(view);
    }


    public GoodsAdvertisementForSearchResponse convertGoodsAdvertisementsToSearchResponse(GoodsAdvertisement advertisement) {
        return goodsAdvertisementsToSearchResponse(advertisement);
    }

    public GoodsAdvertisementForSearchResponse convertGoodsAdvertisementsToSearchResponse(GoodsAdvertisement advertisement, Currency currency) {
        GoodsAdvertisementForSearchResponse response = goodsAdvertisementsToSearchResponse(advertisement);
        if (advertisement.hasCurrency(currency)) {
            response.setMinPrice(advertisement.getMinPrice(currency));
            response.setMaxPrice(advertisement.getMaxPrice(currency));
            response.setMaxPriceWithDiscount(advertisement.getMaxPriceWithDiscounts(currency));
            response.setMinPriceWithDiscount(advertisement.getMinPriceWithDiscounts(currency));
        } else {
            response.setMinPrice(currencyService.exchangeCurrencyFromDollar(response.getMinPrice(), currency.getCode()));
            response.setMaxPrice(currencyService.exchangeCurrencyFromDollar(response.getMaxPrice(), currency.getCode()));
            response.setMaxPriceWithDiscount(currencyService.exchangeCurrencyFromDollar(response.getMaxPriceWithDiscount(), currency.getCode()));
            response.setMinPriceWithDiscount(currencyService.exchangeCurrencyFromDollar(response.getMinPriceWithDiscount(), currency.getCode()));
        }
        response.setCurrencyCode(currency.getCode());
        return response;
    }

    public GoodsAdvertisementForSearchResponse goodsAdvertisementsToSearchResponse(GoodsAdvertisement advertisement) {
        GoodsAdvertisementForSearchResponse response = new GoodsAdvertisementForSearchResponse();
        response.setId(advertisement.getId());
        response.setTitle(advertisement.getTitle());
        response.setImage(advertisement.getMainImage());
        response.setSeller(advertisement.getSeller().getShopName());
        response.setSellerId(advertisement.getSeller().getId());
        response.setDate(advertisement.getCreationDate());
        response.setHasDiscount(advertisement.hasDiscount());
        response.setMaxPrice(advertisement.getMaxPrice(null));
        response.setMinPrice(advertisement.getMinPrice(null));
        response.setMaxPriceWithDiscount(advertisement.getMaxPriceWithDiscounts(null));
        response.setMinPriceWithDiscount(advertisement.getMinPriceWithDiscounts(null));
        response.setSold((long) advertisement.getOrderItems().stream().mapToInt(GoodsOrderItem::getCount).sum());
        response.setRating(goodsAdvertisementFeedbackRepository.getAverageRatingByGoodsAdvertisement(advertisement.getId()).orElse(null));
        return response;
    }


    public PageResponse<GoodsAdvertisementForSearchResponse> findPageBySearchRequest(GoodsAdvertisementSearchRequest request) {
        System.out.println(request.getPaginationRequest().getPage());
        final Page<GoodsAdvertisement> page = goodsAdvertisementRepository.findAll(
                new GoodsAdvertisementSpecification(request),
                request.getPaginationRequest().mapToPageable()
        );


        request.setCurrencyCode(currencyService.auditUserCurrency(request.getCurrencyCode()));

        return new PageResponse<>(page.get()
                .map((a) -> convertGoodsAdvertisementsToSearchResponse(a, currencyService.findCurrencyByCurrencyCode(request.getCurrencyCode())))
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages());
    }


}
