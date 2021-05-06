package rostyk.stupnytskiy.andromeda.dto.response.account.goods_seller.category;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.categories.GoodsSellerAdvertisementCategory;

@Getter
@Setter
public class GoodsSellerAdvertisementCategoryResponse {
    private Long id;
    private String title;
    private boolean hasChildren;

    public GoodsSellerAdvertisementCategoryResponse(GoodsSellerAdvertisementCategory category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.hasChildren = category.hasChildren();
    }
}
