package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.PropertyRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.Property;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailPrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private PropertyService propertyService;


    public GoodsAdvertisement findById(Long id) {
        return goodsAdvertisementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsAdvertisement findByIdAndSeller(Long id) {
        return goodsAdvertisementRepository.findByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder()).orElseThrow(IllegalArgumentException::new);
    }


    public void saveRetailGoodsAdvertisement(RetailGoodsAdvertisementRequest request) {
        RetailGoodsAdvertisement advertisement =
                advertisementRepository.save(new RetailGoodsAdvertisement(goodsAdvertisementFromRequest(request)));
        RetailPrice price = retailGoodsAdvertisementService.finishAdvertisementCreation(advertisement, request);
        advertisement.setPriceToSort(price.getPrice());
        advertisementRepository.save(advertisement);
    }

    public void saveWholesaleGoodsAdvertisement(WholesaleGoodsAdvertisementRequest request) {
        wholesaleGoodsAdvertisementService.validWholesaleUnit(request.getPrice());
        WholesaleGoodsAdvertisement advertisement =
                advertisementRepository.save(new WholesaleGoodsAdvertisement(goodsAdvertisementFromRequest(request)));
        WholesalePrice price = wholesaleGoodsAdvertisementService.finishAdvertisementCreation(advertisement, request);
        advertisement.setPriceToSort(price.getMinPrice());
        advertisementRepository.save(advertisement);
    }

    public void exchangePriceForAll() {
        goodsAdvertisementRepository.findAll().forEach((a) -> {
            a.setPriceToSort(
                    Math.round(a.getPriceForSort() * 100) / 100.0
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


    public void minusAdvertisementCount(Long goodsAdvertisementId, Integer minus) {
        GoodsAdvertisement advertisement = findById(goodsAdvertisementId);
        advertisement.setCount(advertisement.getCount() - minus);
        goodsAdvertisementRepository.save(advertisement);
    }

    public PageResponse<AdvertisementResponse> findAllSellerAdvertisementsPage(Long id, PaginationRequest request) {
        if (id == null) {
            id = goodsSellerAccountService.findBySecurityContextHolder().getId();
        }
        Page<GoodsAdvertisement> page = goodsAdvertisementRepository.findPageBySellerId(id, request.mapToPageable());
        return new PageResponse<>(page
                .get()
                .map(GoodsAdvertisement::mapToResponse)
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
        advertisement.setCount(count);
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
}
