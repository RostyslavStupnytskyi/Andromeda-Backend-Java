package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.category;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories.GoodsSellerAdvertisementCategory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GoodsSellerAdvertisementCategoryWithChildrenResponse {
    private GoodsSellerAdvertisementCategoryResponse category;
    private List<GoodsSellerAdvertisementCategoryWithChildrenResponse> children;

    public GoodsSellerAdvertisementCategoryWithChildrenResponse(GoodsSellerAdvertisementCategory category) {
        this.category = new GoodsSellerAdvertisementCategoryResponse(category);
        this.children = category.getChildrenCategories()
                .stream()
                .map(GoodsSellerAdvertisementCategoryWithChildrenResponse::new)
                .collect(Collectors.toList());
    }

}
