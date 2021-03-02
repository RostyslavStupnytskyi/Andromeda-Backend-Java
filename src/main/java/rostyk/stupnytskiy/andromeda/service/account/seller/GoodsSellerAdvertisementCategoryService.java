package rostyk.stupnytskiy.andromeda.service.account.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.seller_categories.GoodsSellerAdvertisementCategoryRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.category.GoodsSellerAdvertisementCategoryResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.category.GoodsSellerAdvertisementCategoryWithChildrenResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories.GoodsSellerAdvertisementCategory;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.GoodsSellerAdvertisementCategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsSellerAdvertisementCategoryService {

    @Autowired
    private GoodsSellerAdvertisementCategoryRepository goodsSellerAdvertisementCategoryRepository;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;


    public GoodsSellerAdvertisementCategory findById(Long id) {
        return goodsSellerAdvertisementCategoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void save(GoodsSellerAdvertisementCategoryRequest request) {
        goodsSellerAdvertisementCategoryRepository.save(goodsSellerAdvertisementCategoryRequestToGoodsSellerAdvertisementCategory(request, null));
    }

    public void update(GoodsSellerAdvertisementCategoryRequest request, Long id) {
        goodsSellerAdvertisementCategoryRepository.save(goodsSellerAdvertisementCategoryRequestToGoodsSellerAdvertisementCategory(request, findById(id)));
    }



    public List<GoodsSellerAdvertisementCategoryResponse> getRootCategories() {
        List<GoodsSellerAdvertisementCategory> categories = goodsSellerAdvertisementCategoryRepository.findAllByGoodsSeller(goodsSellerAccountService.findBySecurityContextHolder());
        return categories.stream()
                .filter(GoodsSellerAdvertisementCategory::isRootLevelCategory)
                .map(GoodsSellerAdvertisementCategoryResponse::new)
                .collect(Collectors.toList());
    }

    public List<GoodsSellerAdvertisementCategoryWithChildrenResponse> getSellerCategoriesTree(Long id) {
        List<GoodsSellerAdvertisementCategory> categories = goodsSellerAdvertisementCategoryRepository.findAllByGoodsSeller(goodsSellerAccountService.findById(id));
        return categories.stream()
                .filter(GoodsSellerAdvertisementCategory::isRootLevelCategory)
                .map(GoodsSellerAdvertisementCategoryWithChildrenResponse::new)
                .collect(Collectors.toList());
    }

    public List<GoodsSellerAdvertisementCategoryResponse> getInheritLineFromRootToCategory(Long id) {
        List<GoodsSellerAdvertisementCategoryResponse> response = new ArrayList<>();
        GoodsSellerAdvertisementCategory category = findById(id);

        do {
            response.add(0, new GoodsSellerAdvertisementCategoryResponse(category));
            category = category.getParentCategory();
        } while (category != null);

        return response;
    }

    private GoodsSellerAdvertisementCategory goodsSellerAdvertisementCategoryRequestToGoodsSellerAdvertisementCategory(GoodsSellerAdvertisementCategoryRequest request, GoodsSellerAdvertisementCategory category) {
        if (category == null) {
            category = new GoodsSellerAdvertisementCategory();
            if (request.getParentId() != null) {
                category.setParentCategory(findById(request.getParentId()));
            }
        }
        category.setTitle(request.getTitle());
        category.setGoodsSeller(goodsSellerAccountService.findBySecurityContextHolder());
        return category;
    }

}
